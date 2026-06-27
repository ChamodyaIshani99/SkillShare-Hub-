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
public class ProgressResponse {

    private String id;

    private Boolean completed;

    private Integer watchTime;

    private Integer completionPercentage;

    private LocalDateTime lastAccessed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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

    private String courseThumbnail;

    private Integer totalLessons;

    // ==========================
    // Lesson Details
    // ==========================

    private String lessonId;

    private String lessonTitle;

    private Integer lessonNumber;

    private Integer lessonDuration;

    private Boolean preview;

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

}