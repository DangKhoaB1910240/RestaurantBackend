package com.example.event.reservation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.event.cart.Cart;
import com.example.event.cart.CartDto;
import com.example.event.cart.CartRepository;
import com.example.event.cart.CartService;
import com.example.event.email.Email;
import com.example.event.email.EmailService;
import com.example.event.exception.NotFoundException;
import com.example.event.item.Item;
import com.example.event.item.ItemRepository;
import com.example.event.logger.LoggerService;
import com.example.event.reservationitem.ReservationItem;
import com.example.event.reservationitem.ReservationItemDto;
import com.example.event.reservationitem.ReservationItemRepository;
import com.example.event.role.Role;
import com.example.event.table.Table;
import com.example.event.table.TableRepository;
import com.example.event.user.User;
import com.example.event.user.UserRepository;
import com.example.event.vnpay.VNPayResource;

import jakarta.persistence.EntityNotFoundException;
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
    private final LoggerService loggerService;

    public List<Reservation> getReservationsByUser(Integer userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation adminOrderFood(AdminReservationRequest request) {
        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
        }

        Table table = null;
        Integer orderType = 1; // Mặc định là mang đi
        if (request.getTableId() != null) {
            table = tableRepository.findById(request.getTableId())
                    .filter(Table::getIsAvailable)
                    .orElseThrow(() -> new NotFoundException("Không có bàn trống"));
            orderType = 0; // Nếu có bàn thì ăn tại chỗ
        }

        // Tạo đơn đặt món ăn
        Reservation reservation = new Reservation();
        reservation.setUser(user); // Có thể null
        reservation.setTable(table);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setDepositFee(0L); // Không cần cọc khi chỉ gọi món
        reservation.setStatus(0); // Chưa xử lý
        reservation.setOrderType(orderType);

        reservation = reservationRepository.save(reservation);

        // Thêm món ăn vào đơn hàng
        List<ReservationItem> reservationItems = new ArrayList<>();
        long totalAmount = 0L;
        for (ItemRequest itemRequest : request.getItems()) {
            Item item = itemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy món ăn"));

            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setReservation(reservation);
            reservationItem.setItem(item);
            reservationItem.setQuantity(itemRequest.getQuantity());
            reservationItem.setTotalPrice(item.getCost() * itemRequest.getQuantity());

            reservationItems.add(reservationItem);
            totalAmount += reservationItem.getTotalPrice();
        }
        reservationItemRepository.saveAll(reservationItems);

        // Cập nhật tổng tiền
        reservation.setTotalAmount(totalAmount);
        reservationRepository.save(reservation);

        return reservation;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lịch đặt bàn không tồn tại: " + id));
    }

    // @Scheduled(fixedRate = 1000) // Chạy mỗi giây (1,000ms)
    // public void deleteUnpaidReservations() {
    // LocalDateTime oneSecondAgo = LocalDateTime.now().minusSeconds(1);
    // List<Reservation> unpaidReservations =
    // reservationRepository.findByStatusAndNgayTaoBefore(0, oneSecondAgo);

    // reservationRepository.deleteAll(unpaidReservations);
    // if (!unpaidReservations.isEmpty()) {
    // System.out.println("Đã xóa " + unpaidReservations.size() + " đơn chưa thanh
    // toán");
    // }
    // }

    @Scheduled(fixedRate = 900000) // Chạy mỗi 15 phút (900,000ms)
    public void deleteUnpaidReservations() {
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minusMinutes(15);
        List<Reservation> unpaidReservations = reservationRepository.findByStatusAndNgayTaoBefore(0, fifteenMinutesAgo);

        reservationRepository.deleteAll(unpaidReservations);
        System.out.println("Đã xóa " + unpaidReservations.size() + " đơn chưa thanh toán");
    }

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
        Long tongGia = 0L;
        for (Cart cart : carts) {
            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setReservation(savedReservation);
            reservationItem.setItem(cart.getItem());
            reservationItem.setQuantity(cart.getQuantity());
            reservationItem.setTotalPrice(cart.getItem().getCost() * cart.getQuantity()); // Giả sử có getPrice()

            reservationItems.add(reservationItem);
            tongGia += cart.getItem().getCost() * cart.getQuantity();
        }
        reservationItemRepository.saveAll(reservationItems); // Lưu tất cả vào DB
        savedReservation.setTotalAmount(tongGia);
        reservationRepository.save(savedReservation);
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

    public List<Reservation> getAllRegistrationByFilter(
            Integer tabledId,
            String userFullname,
            Integer status,
            String monthYear,
            String dayMonthYear) {
        // Lấy danh sách tổng ra
        List<Reservation> reservations = reservationRepository.findAll();
        // Lấy user theo userFullname
        if (userFullname != null) {
            reservations = reservationRepository.findByUserFullnameOrUsername(userFullname);

        }
        if (tabledId != null) {
            reservations.removeIf(r -> r.getTable().getId() != tabledId);
        }
        // Filter by status
        if (status != null) {
            reservations.removeIf(r -> r.getStatus() != status);
        }
        // Filter by month and year
        if (monthYear != null && !monthYear.trim().isEmpty() && monthYear != "null") {
            try {
                DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
                LocalDate startDate = LocalDate.parse("01/" + monthYear, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                reservations.removeIf(
                        r -> r.getReservationTime().toLocalDate().isBefore(startDate)
                                || r.getReservationTime().toLocalDate().isAfter(endDate));

            } catch (DateTimeParseException e) {
                // Handle the exception or log it
                e.printStackTrace();
            }
        }

        // Filter by day, month, and year
        if (dayMonthYear != null && !dayMonthYear.trim().isEmpty() && dayMonthYear != "null") {
            try {
                DateTimeFormatter dayMonthYearFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate specificDate = LocalDate.parse(dayMonthYear, dayMonthYearFormatter);
                reservations.removeIf(r -> !r.getReservationTime().toLocalDate().equals(specificDate));
            } catch (DateTimeParseException e) {
                // Handle the exception or log it
                e.printStackTrace();
            }
        }
        return reservations;

    }

    public void updateById(Integer id, Integer userId, Reservation reservation) {
        Reservation reservation2 = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại lịch đặt bàn này"));
        reservation2.setStatus(reservation.getStatus());
        String trangThai;
        switch (reservation2.getStatus()) {
            case 0:
                trangThai = "Chưa xử lý";
                break;
            case 1:
                trangThai = "Đã cọc";
                break;
            case 2:
                trangThai = "Đã nhận bàn";
                break;
            case 3:
                trangThai = "Đã hủy bỏ";
                break;
            default:
                trangThai = "Không xác định";
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tồn tại người dùng này"));
        addLogger(userId, "- Cập nhật trạng thái tài khoản \"" + user.getUsername()
                + "\" thành " + trangThai);
        reservationRepository.save(reservation2);
    }

    private void addLogger(Integer userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tồn tại người dùng "));
        String roleName = new String();
        boolean lastElement = false;

        // Lặp qua các role
        for (int i = 0; i < user.getRoles().size(); i++) {
            Role r = user.getRoles().get(i);
            roleName += r.getName();

            // Kiểm tra nếu phần tử hiện tại là phần tử cuối cùng
            if (i == user.getRoles().size() - 1) {
                lastElement = true;
            }

            // Nếu không phải phần tử cuối cùng, thêm dấu phẩy
            if (!lastElement) {
                roleName += " ,";
            }
        }
        loggerService.addLogger(user, content, roleName);
    }

    public void exportToExcel(List<Reservation> reservations, OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reservations");

        Row headerRow = sheet.createRow(0);
        String[] headers = { "STT", "Khách hàng", "Tài khoản", "Loại đơn hàng", "Bàn số", "Loại bàn", "Ngày đặt bàn",
                "Ngày nhận bàn", "Tiền cọc", "Trạng thái" };

        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm"));

        int rowNum = 1;
        int stt = 1;
        for (Reservation r : reservations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(r.getUser() != null ? r.getUser().getFullname() : "");
            row.createCell(2).setCellValue(r.getUser() != null ? r.getUser().getUsername() : "");

            // Loại đơn hàng
            String orderType = r.getOrderType() == 1 ? "Mang đi" : "Ăn tại chỗ";
            row.createCell(3).setCellValue(orderType);

            // Bàn số
            row.createCell(4).setCellValue(r.getTable() != null ? r.getTable().getTableNumber() : "");

            // Loại bàn
            String tableType = (r.getTable() != null && r.getTable().getType() == 0) ? "Bàn thường" : "Bàn VIP";
            row.createCell(5).setCellValue(tableType);

            // Ngày đặt bàn
            Cell createDateCell = row.createCell(6);
            createDateCell.setCellValue(r.getNgayTao());
            createDateCell.setCellStyle(dateCellStyle);

            // Ngày nhận bàn
            Cell reservationDateCell = row.createCell(7);
            reservationDateCell.setCellValue(r.getReservationTime());
            reservationDateCell.setCellStyle(dateCellStyle);

            row.createCell(8).setCellValue(r.getDepositFee() + "đ");

            // Trạng thái đặt bàn
            String statusText = switch (r.getStatus()) {
                case 0 -> "Chưa xử lý";
                case 1 -> "Đã cọc";
                case 2 -> "Đã nhận bàn";
                case 3 -> "Đã hủy bỏ";
                default -> "Không xác định";
            };
            row.createCell(9).setCellValue(statusText);
        }

        workbook.write(outputStream);
        workbook.close();
    }

}
