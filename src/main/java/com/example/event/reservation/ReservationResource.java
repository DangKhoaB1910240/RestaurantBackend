package com.example.event.reservation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.reservationitem.ReservationItemDto;
import com.example.event.reservationitem.ReservationItemService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/reservation")
@CrossOrigin(origins = { "http://localhost:4200" })

@RequiredArgsConstructor
public class ReservationResource {

    private final ReservationService reservationService;

    @GetMapping()
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getReservationsById(@PathVariable Integer id) {
        Reservation reservations = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservations);
    }

    @PatchMapping("{id}/user/{userId}")
    public ResponseEntity<Void> updateById(@PathVariable Integer id, @PathVariable Integer userId,
            @RequestBody Reservation reservation) {
        reservationService.updateById(id, userId, reservation);
        return ResponseEntity.noContent().build();
    }

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

    @GetMapping("/filter")
    public ResponseEntity<List<Reservation>> getAllRegistrationByFilter(
            @RequestParam(required = false) Integer tableId,
            @RequestParam(required = false) String userFullname,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String monthYear,
            @RequestParam(required = false) String dayMonthYear) {
        return ResponseEntity.status(HttpStatus.OK).body(
                reservationService.getAllRegistrationByFilter(tableId, userFullname, status, monthYear, dayMonthYear));
    }

    @GetMapping("/export-excel")
    public void exportToExcel(
            @RequestParam(required = false) Integer tableId,
            @RequestParam(required = false) String userFullname,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String monthYear,
            @RequestParam(required = false) String dayMonthYear,
            HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reservations.xlsx");

        List<Reservation> reservations = reservationService.getAllRegistrationByFilter(
                tableId, userFullname, status, monthYear, dayMonthYear);

        reservationService.exportToExcel(reservations, response.getOutputStream());
    }

}
