package com.example.event.table;

import lombok.Data;

@Data
public class TableDto {
    private String tableNumber;
    private Integer capacity;
    private Boolean isAvailable;
    private Integer type;
    private Long price;
}
