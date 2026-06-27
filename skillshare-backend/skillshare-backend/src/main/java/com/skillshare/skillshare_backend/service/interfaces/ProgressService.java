package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.ProgressRequest;
import com.skillshare.skillshare_backend.dto.response.ProgressResponse;

import java.util.List;

public interface ProgressService {

    // ==========================================
    // Student APIs
    // ==========================================

    // Create progress for a lesson
    ProgressResponse create(ProgressRequest request);

    // Update lesson progress
    ProgressResponse update(String progressId, ProgressRequest request);

    // Get progress by ID
    ProgressResponse getById(String progressId);

    // Get my progress for a course
    List<ProgressResponse> getMyProgressByCourse(String courseId);

    // Get my progress for all courses
    List<ProgressResponse> getMyProgress();

    // ==========================================
    // Instructor APIs
    // ==========================================

    // View all students' progress in a course
    List<ProgressResponse> getCourseProgress(String courseId);

    // ==========================================
    // Admin APIs
    // ==========================================

    // Get all progress records
    List<ProgressResponse> getAllProgress();

}