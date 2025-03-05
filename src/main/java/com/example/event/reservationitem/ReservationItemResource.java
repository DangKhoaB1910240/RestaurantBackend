package com.example.event.reservationitem;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/reservation-item")
@CrossOrigin(origins = { "http://localhost:4200" })

@RequiredArgsConstructor
public class ReservationItemResource {

    private final ReservationItemService reservationItemService;

    @PostMapping("/add")
    public ResponseEntity<?> addReservationItem(@RequestBody ReservationItemDto dto) {
        return ResponseEntity.ok(reservationItemService.addReservationItem(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReservationItem(@PathVariable Integer id, @RequestBody ReservationItemDto dto) {
        return ResponseEntity.ok(reservationItemService.updateReservationItem(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservationItem(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationItemService.deleteReservationItem(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationItem>> getAllReservationItems() {
        return ResponseEntity.ok(reservationItemService.getAllReservationItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationItem> getReservationItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationItemService.getReservationItemById(id));
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity<List<ReservationItem>> getReservationItemsByReservationId(
            @PathVariable Integer reservationId) {
        return ResponseEntity.ok(reservationItemService.getReservationItemsByReservationId(reservationId));
    }
}
