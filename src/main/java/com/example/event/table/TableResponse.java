package com.example.event.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableResponse {
    private Integer id;
    private String tableNumber;
    private Integer capacity;
    private Boolean isAvailable;
    private Integer type;
    private Long price;

    public static TableResponse fromEntity(Table table) {
        return new TableResponse(
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getIsAvailable(),
                table.getType(),
                table.getPrice());
    }
}
