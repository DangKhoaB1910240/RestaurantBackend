package com.example.event.reservation;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.event.cart.Cart;
import com.example.event.cart.CartRepository;
import com.example.event.cart.CartService;
import com.example.event.email.Email;
import com.example.event.email.EmailService;
import com.example.event.exception.NotFoundException;
import com.example.event.item.Item;
import com.example.event.item.ItemRepository;
import com.example.event.reservationitem.ReservationItem;
import com.example.event.reservationitem.ReservationItemDto;
import com.example.event.reservationitem.ReservationItemRepository;
import com.example.event.table.Table;
import com.example.event.table.TableRepository;
import com.example.event.user.User;
import com.example.event.user.UserRepository;
import com.example.event.vnpay.VNPayResource;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ReservationItemRepository reservationItemRepository;
    private final ItemRepository itemRepository;
    private final EmailService emailService;
    private final VNPayResource vnPayResource;
    private final CartService cartService;

    @Transactional
    public String bookTable(ReservationDto request) throws UnsupportedEncodingException {
        Optional<User> userOpt = userRepository.findById(request.getUserId());

        if (userOpt.isEmpty()) {
            throw new NotFoundException("Không tồn tại người dùng này");
        }

        Table table = null;
        if (request.getTableId() != null) { // Nếu có tableId thì kiểm tra
            System.out.println(request.getTableId());
            table = tableRepository.findById(request.getTableId())
                    .filter(Table::getIsAvailable)
                    .orElseThrow(() -> new NotFoundException("Không có bàn trống phù hợp"));
        }

        // Đặt bàn
        Reservation reservation = new Reservation();
        reservation.setUser(userOpt.get());
        reservation.setTable(table);
        reservation.setReservationTime(request.getReservationTime());
        reservation.setDepositFee(request.getDepositFee());
        reservation.setStatus(0); // Chưa xử lý
        reservation.setOrderType(request.getOrderType());
        Reservation savedReservation = reservationRepository.save(reservation);
        Integer reservationId = savedReservation.getId();
        List<Cart> carts = cartService.getCartItems(userOpt.get().getId());

        // 3️⃣ Lưu từng Cart vào ReservationItem
        List<ReservationItem> reservationItems = new ArrayList<>();
        for (Cart cart : carts) {
            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setReservation(savedReservation);
            reservationItem.setItem(cart.getItem());
            reservationItem.setQuantity(cart.getQuantity());
            reservationItem.setTotalPrice(cart.getItem().getCost() * cart.getQuantity()); // Giả sử có getPrice()

            reservationItems.add(reservationItem);
        }
        reservationItemRepository.saveAll(reservationItems); // Lưu tất cả vào DB

        // Xóa giỏ hàng của user
        cartRepository.deleteByUserId(request.getUserId());

        Email email = new Email();
        email.setSubject("THÔNG BÁO ĐẶT BÀN THÀNH CÔNG");
        email.setToEmail(userOpt.get().getEmail());
        emailService.sendEmail(email, userOpt.get(), table, reservation.getDepositFee(), reservation);
        return vnPayResource.getPay(reservation.getDepositFee(), reservationId);

    }

    @Transactional
    public String addItemToReservation(ReservationItemDto request) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(request.getReservationId());
        Optional<Item> itemOpt = itemRepository.findById(request.getItemId());

        if (reservationOpt.isEmpty() || itemOpt.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString());
        }

        Reservation reservation = reservationOpt.get();
        if (reservation.getStatus() != 1) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST.toString());
        }

        ReservationItem reservationItem = new ReservationItem();
        reservationItem.setReservation(reservation);
        reservationItem.setItem(itemOpt.get());
        reservationItem.setQuantity(request.getQuantity());
        reservationItem.setTotalPrice(request.getTotalPrice());

        reservationItemRepository.save(reservationItem);

        return "Item added to reservation";
    }
}
