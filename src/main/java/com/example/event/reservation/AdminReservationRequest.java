package com.example.event.reservation;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminReservationRequest {
    private Integer userId;
    private Integer tableId; // Có thể null
    private LocalDateTime reservationTime;
    private Long depositFee;
    private List<ItemRequest> items; // Danh sách món ăn
}
