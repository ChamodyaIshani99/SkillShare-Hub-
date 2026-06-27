package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.request.LessonRequest;
import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.LessonResponse;
import com.skillshare.skillshare_backend.service.interfaces.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LessonController {

    private final LessonService lessonService;

    // ==========================================
    // Instructor APIs
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<LessonResponse>> createLesson(
            @Valid @RequestBody LessonRequest request) {

        LessonResponse response = lessonService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LessonResponse>builder()
                        .success(true)
                        .message("Lesson created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLesson(
            @PathVariable String id,
            @Valid @RequestBody LessonRequest request) {

        LessonResponse response = lessonService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.<LessonResponse>builder()
                        .success(true)
                        .message("Lesson updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLesson(
            @PathVariable String id) {

        lessonService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Lesson deleted successfully")
                        .data("Lesson deleted successfully")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ==========================================
    // Student APIs
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> getLessonById(
            @PathVariable String id) {

        LessonResponse response = lessonService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<LessonResponse>builder()
                        .success(true)
                        .message("Lesson retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getLessonsByCourse(
            @PathVariable String courseId) {

        List<LessonResponse> response = lessonService.getByCourse(courseId);

        return ResponseEntity.ok(
                ApiResponse.<List<LessonResponse>>builder()
                        .success(true)
                        .message("Lessons retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/course/{courseId}/preview")
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getPreviewLessons(
            @PathVariable String courseId) {

        List<LessonResponse> response = lessonService.getPreviewLessons(courseId);

        return ResponseEntity.ok(
                ApiResponse.<List<LessonResponse>>builder()
                        .success(true)
                        .message("Preview lessons retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ==========================================
    // Admin APIs
    // ==========================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getAllLessons() {

        List<LessonResponse> response = lessonService.getAll();

        return ResponseEntity.ok(
                ApiResponse.<List<LessonResponse>>builder()
                        .success(true)
                        .message("All lessons retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}