package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.ReviewRequest;
import com.skillshare.skillshare_backend.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    // ==========================================
    // Student APIs
    // ==========================================

    // Create a review
    ReviewResponse create(ReviewRequest request);

    // Update a review
    ReviewResponse update(String reviewId, ReviewRequest request);

    // Delete a review
    void delete(String reviewId);

    // Get my reviews
    List<ReviewResponse> getMyReviews();

    // ==========================================
    // Public APIs
    // ==========================================

    // Get review by ID
    ReviewResponse getById(String reviewId);

    // Get all reviews of a course
    List<ReviewResponse> getByCourse(String courseId);

    // ==========================================
    // Admin APIs
    // ==========================================

    // Get all reviews
    List<ReviewResponse> getAllReviews();

}