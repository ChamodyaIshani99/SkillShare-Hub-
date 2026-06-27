package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.request.CourseRequest;
import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.CourseResponse;
import com.skillshare.skillshare_backend.service.interfaces.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    // ===========================
    // Instructor APIs
    // ===========================

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Valid @RequestBody CourseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CourseResponse>builder()
                        .success(true)
                        .message("Course created successfully")
                        .data(courseService.create(request))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable String id,
            @Valid @RequestBody CourseRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CourseResponse>builder()
                        .success(true)
                        .message("Course updated successfully")
                        .data(courseService.update(id, request))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @PathVariable String id) {

        courseService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Course deleted successfully")
                        .data("Deleted")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<CourseResponse>> publishCourse(
            @PathVariable String id) {

        return ResponseEntity.ok(
                ApiResponse.<CourseResponse>builder()
                        .success(true)
                        .message("Course published successfully")
                        .data(courseService.publish(id))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PatchMapping("/{id}/unpublish")
    public ResponseEntity<ApiResponse<CourseResponse>> unpublishCourse(
            @PathVariable String id) {

        return ResponseEntity.ok(
                ApiResponse.<CourseResponse>builder()
                        .success(true)
                        .message("Course unpublished successfully")
                        .data(courseService.unpublish(id))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/my-courses")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getMyCourses() {

        return ResponseEntity.ok(
                ApiResponse.<List<CourseResponse>>builder()
                        .success(true)
                        .message("My courses retrieved successfully")
                        .data(courseService.getMyCourses())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ===========================
    // Student APIs
    // ===========================

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getPublishedCourses() {

        return ResponseEntity.ok(
                ApiResponse.<List<CourseResponse>>builder()
                        .success(true)
                        .message("Courses retrieved successfully")
                        .data(courseService.getAllPublishedCourses())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                ApiResponse.<CourseResponse>builder()
                        .success(true)
                        .message("Course retrieved successfully")
                        .data(courseService.getById(id))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesBySkill(
            @PathVariable String skillId) {

        return ResponseEntity.ok(
                ApiResponse.<List<CourseResponse>>builder()
                        .success(true)
                        .message("Courses retrieved successfully")
                        .data(courseService.getBySkill(skillId))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchCourses(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                ApiResponse.<List<CourseResponse>>builder()
                        .success(true)
                        .message("Search completed successfully")
                        .data(courseService.search(keyword))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ===========================
    // Admin API
    // ===========================

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {

        return ResponseEntity.ok(
                ApiResponse.<List<CourseResponse>>builder()
                        .success(true)
                        .message("All courses retrieved successfully")
                        .data(courseService.getAll())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}