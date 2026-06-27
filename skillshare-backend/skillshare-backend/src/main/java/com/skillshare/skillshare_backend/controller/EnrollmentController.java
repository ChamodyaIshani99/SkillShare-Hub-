package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.request.EnrollmentRequest;
import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.EnrollmentResponse;
import com.skillshare.skillshare_backend.service.interfaces.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // ==========================================
    // Student APIs
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponse>> enroll(
            @Valid @RequestBody EnrollmentRequest request) {

        EnrollmentResponse response = enrollmentService.enroll(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<EnrollmentResponse>builder()
                        .success(true)
                        .message("Successfully enrolled in the course")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> cancelEnrollment(
            @PathVariable String id) {

        enrollmentService.cancelEnrollment(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Enrollment cancelled successfully")
                        .data("Enrollment cancelled successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/my-courses")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getMyEnrollments() {

        List<EnrollmentResponse> response = enrollmentService.getMyEnrollments();

        return ResponseEntity.ok(
                ApiResponse.<List<EnrollmentResponse>>builder()
                        .success(true)
                        .message("My enrollments retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getEnrollmentById(
            @PathVariable String id) {

        EnrollmentResponse response = enrollmentService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<EnrollmentResponse>builder()
                        .success(true)
                        .message("Enrollment retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> updateProgress(
            @PathVariable String id,
            @RequestParam Integer progress) {

        EnrollmentResponse response =
                enrollmentService.updateProgress(id, progress);

        return ResponseEntity.ok(
                ApiResponse.<EnrollmentResponse>builder()
                        .success(true)
                        .message("Progress updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> markAsCompleted(
            @PathVariable String id) {

        EnrollmentResponse response =
                enrollmentService.markAsCompleted(id);

        return ResponseEntity.ok(
                ApiResponse.<EnrollmentResponse>builder()
                        .success(true)
                        .message("Course marked as completed")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Instructor APIs
    // ==========================================

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getEnrollmentsByCourse(
            @PathVariable String courseId) {

        List<EnrollmentResponse> response =
                enrollmentService.getEnrollmentsByCourse(courseId);

        return ResponseEntity.ok(
                ApiResponse.<List<EnrollmentResponse>>builder()
                        .success(true)
                        .message("Course enrollments retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Admin APIs
    // ==========================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAllEnrollments() {

        List<EnrollmentResponse> response =
                enrollmentService.getAllEnrollments();

        return ResponseEntity.ok(
                ApiResponse.<List<EnrollmentResponse>>builder()
                        .success(true)
                        .message("All enrollments retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}