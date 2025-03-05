package com.example.event.reservationitem;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.event.exception.NotFoundException;
import com.example.event.item.Item;
import com.example.event.item.ItemRepository;
import com.example.event.reservation.Reservation;
import com.example.event.reservation.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationItemService {

    private final ReservationItemRepository reservationItemRepository;
    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;

    public String addReservationItem(ReservationItemDto dto) {
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));

        ReservationItem reservationItem = new ReservationItem();
        reservationItem.setReservation(reservation);
        reservationItem.setItem(item);
        reservationItem.setQuantity(dto.getQuantity());
        reservationItem.setTotalPrice(dto.getTotalPrice());

        reservationItemRepository.save(reservationItem);
        return "Reservation item added successfully";
    }

    public String updateReservationItem(Integer id, ReservationItemDto dto) {
        ReservationItem reservationItem = reservationItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));
        ;

        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));
        ;

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));
        ;

        reservationItem.setReservation(reservation);
        reservationItem.setItem(item);
        reservationItem.setQuantity(dto.getQuantity());
        reservationItem.setTotalPrice(dto.getTotalPrice());

        reservationItemRepository.save(reservationItem);
        return "Reservation item updated successfully";
    }

    public String deleteReservationItem(Integer id) {
        if (!reservationItemRepository.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString());
        }
        reservationItemRepository.deleteById(id);
        return "Reservation item deleted successfully";
    }

    public List<ReservationItem> getAllReservationItems() {
        return reservationItemRepository.findAll();
    }

    public ReservationItem getReservationItemById(Integer id) {
        ReservationItem reservationItem = reservationItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));
        return reservationItem;
    }

    public List<ReservationItem> getReservationItemsByReservationId(Integer reservationId) {
        return reservationItemRepository.findByReservationId(reservationId);
    }
}
