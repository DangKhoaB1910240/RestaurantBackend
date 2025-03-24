package com.example.event.review;

import lombok.Data;

@Data
public class ReviewDTO {
    private Integer userId;
    private String content;
    private Integer rating; // 1-5

    // Getters and Setters
}