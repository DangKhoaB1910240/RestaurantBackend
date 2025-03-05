package com.example.event.reservation;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReservationDto {
    private Integer userId;
    private Integer tableId;
    private LocalDateTime reservationTime;
    private Long depositFee;
    private Integer orderType;
}
