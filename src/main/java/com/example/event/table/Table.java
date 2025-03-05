package com.example.event.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Table(name = "restaurant_table")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tableNumber; // Mã số bàn
    private Integer capacity; // Số lượng ghế
    private Boolean isAvailable = true; // Trạng thái còn trống (true = còn trống, false = đã đặt)
    private Integer type; // Loại bàn (VIP, THUONG) // 0 là thường, 1 là vip

    private Long price; // Giá bàn (chỉ áp dụng cho VIP)
}