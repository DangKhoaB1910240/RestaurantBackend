package com.example.event.reservationitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationItemDto {
    private Integer reservationId;
    private Integer itemId;
    private Integer quantity;
    private Long totalPrice;
}