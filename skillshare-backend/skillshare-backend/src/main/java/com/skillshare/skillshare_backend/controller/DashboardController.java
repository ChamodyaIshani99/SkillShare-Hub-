package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.CourseAnalyticsResponse;
import com.skillshare.skillshare_backend.dto.response.DashboardResponse;
import com.skillshare.skillshare_backend.dto.response.RevenueAnalyticsResponse;
import com.skillshare.skillshare_backend.dto.response.UserAnalyticsResponse;
import com.skillshare.skillshare_backend.service.interfaces.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    // ==========================================
    // Admin Dashboard
    // ==========================================

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<DashboardResponse>> getAdminDashboard() {

        DashboardResponse response =
                dashboardService.getAdminDashboard();

        return ResponseEntity.ok(
                ApiResponse.<DashboardResponse>builder()
                        .success(true)
                        .message("Admin dashboard retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Instructor Dashboard
    // ==========================================

    @GetMapping("/instructor")
    public ResponseEntity<ApiResponse<DashboardResponse>> getInstructorDashboard() {

        DashboardResponse response =
                dashboardService.getInstructorDashboard();

        return ResponseEntity.ok(
                ApiResponse.<DashboardResponse>builder()
                        .success(true)
                        .message("Instructor dashboard retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Student Dashboard
    // ==========================================

    @GetMapping("/student")
    public ResponseEntity<ApiResponse<DashboardResponse>> getStudentDashboard() {

        DashboardResponse response =
                dashboardService.getStudentDashboard();

        return ResponseEntity.ok(
                ApiResponse.<DashboardResponse>builder()
                        .success(true)
                        .message("Student dashboard retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Revenue Analytics
    // ==========================================

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<RevenueAnalyticsResponse>> getRevenueAnalytics() {

        RevenueAnalyticsResponse response =
                dashboardService.getRevenueAnalytics();

        return ResponseEntity.ok(
                ApiResponse.<RevenueAnalyticsResponse>builder()
                        .success(true)
                        .message("Revenue analytics retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Course Analytics
    // ==========================================

    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<CourseAnalyticsResponse>> getCourseAnalytics() {

        CourseAnalyticsResponse response =
                dashboardService.getCourseAnalytics();

        return ResponseEntity.ok(
                ApiResponse.<CourseAnalyticsResponse>builder()
                        .success(true)
                        .message("Course analytics retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // User Analytics
    // ==========================================

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserAnalyticsResponse>> getUserAnalytics() {

        UserAnalyticsResponse response =
                dashboardService.getUserAnalytics();

        return ResponseEntity.ok(
                ApiResponse.<UserAnalyticsResponse>builder()
                        .success(true)
                        .message("User analytics retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}