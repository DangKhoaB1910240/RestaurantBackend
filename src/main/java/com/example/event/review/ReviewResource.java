package com.example.event.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin(origins = { "http://localhost:4200" })

@RequiredArgsConstructor
public class ReviewResource {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(review);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }
}