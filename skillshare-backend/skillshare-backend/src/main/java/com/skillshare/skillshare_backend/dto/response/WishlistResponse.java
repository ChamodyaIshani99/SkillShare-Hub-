package com.skillshare.skillshare_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {

    private String id;

    private LocalDateTime createdAt;

    // ==========================
    // Student Details
    // ==========================

    private String studentId;

    private String studentName;

    private String studentEmail;

    // ==========================
    // Course Details
    // ==========================

    private String courseId;

    private String courseTitle;

    private String courseDescription;

    private String courseThumbnail;

    private Double coursePrice;

    private Integer courseDuration;

    private String courseLevel;

    private String courseLanguage;

    private Double courseRating;

    private Integer totalStudents;

    // ==========================
    // Skill Details
    // ==========================

    private String skillId;

    private String skillTitle;

    // ==========================
    // Category Details
    // ==========================

    private String categoryId;

    private String categoryName;

    // ==========================
    // Instructor Details
    // ==========================

    private String instructorId;

    private String instructorName;

    private String instructorEmail;

    private String instructorProfileImage;

}