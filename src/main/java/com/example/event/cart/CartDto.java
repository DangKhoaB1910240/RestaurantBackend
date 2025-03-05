package com.example.event.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Integer user_id;
    private Integer item_id;
    private Integer quantity;
}
