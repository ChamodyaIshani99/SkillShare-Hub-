package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.EnrollmentRequest;
import com.skillshare.skillshare_backend.dto.response.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    // ==========================================
    // Student APIs
    // ==========================================

    // Enroll in a course
    EnrollmentResponse enroll(EnrollmentRequest request);

    // Cancel enrollment
    void cancelEnrollment(String enrollmentId);

    // Get my enrollments
    List<EnrollmentResponse> getMyEnrollments();

    // Get enrollment details
    EnrollmentResponse getById(String enrollmentId);

    // Update learning progress
    EnrollmentResponse updateProgress(String enrollmentId, Integer progress);

    // Mark course as completed
    EnrollmentResponse markAsCompleted(String enrollmentId);

    // ==========================================
    // Instructor APIs
    // ==========================================

    // Get all students enrolled in a course
    List<EnrollmentResponse> getEnrollmentsByCourse(String courseId);

    // ==========================================
    // Admin APIs
    // ==========================================

    // Get all enrollments
    List<EnrollmentResponse> getAllEnrollments();

}