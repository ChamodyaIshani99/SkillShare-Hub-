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
public class EnrollmentResponse {

    private String id;

    private LocalDateTime enrollmentDate;

    private Boolean completed;

    private Integer progress;

    private Boolean active;

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