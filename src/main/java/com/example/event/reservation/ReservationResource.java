package com.example.event.reservation;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.reservationitem.ReservationItemDto;
import com.example.event.reservationitem.ReservationItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/reservation")
@CrossOrigin(origins = { "http://localhost:4200" })

@RequiredArgsConstructor
public class ReservationResource {

    private final ReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody ReservationDto dto) throws UnsupportedEncodingException {
        return ResponseEntity.ok(reservationService.bookTable(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Integer userId) {
        System.out.println(userId);
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservations);
    }

}
