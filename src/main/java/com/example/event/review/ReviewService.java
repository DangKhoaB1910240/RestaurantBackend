package com.example.event.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event.exception.NotFoundException;
import com.example.event.user.User;
import com.example.event.user.UserRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public Review createReview(ReviewDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setUser(user);
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        // Không cần set createdAt nữa vì @PrePersist sẽ tự xử lý

        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found"));

        reviewRepository.delete(review);
    }
}