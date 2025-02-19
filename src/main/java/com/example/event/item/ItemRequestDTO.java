package com.example.event.item;

import java.time.LocalDateTime;

import com.example.event.category.Category;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {
    private String itemName;
    private String description;
    private String img;
    private Integer categoryId;
    private Long cost;
    private Boolean status = true;
}
