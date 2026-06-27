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
public class LessonResponse {

    private String id;

    private String title;

    private String description;

    private Integer lessonNumber;

    private String videoUrl;

    private String pdfUrl;

    private Integer duration;

    private Boolean preview;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Course Details
    private String courseId;

    private String courseTitle;

    // Skill Details
    private String skillId;

    private String skillTitle;

    // Category Details
    private String categoryId;

    private String categoryName;

    // Instructor Details
    private String instructorId;

    private String instructorName;

    private String instructorEmail;

}