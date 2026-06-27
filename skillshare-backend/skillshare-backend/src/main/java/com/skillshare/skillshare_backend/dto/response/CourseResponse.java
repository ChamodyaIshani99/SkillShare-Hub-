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
public class CourseResponse {

    private String id;

    private String title;

    private String description;

    private String thumbnail;

    private Double price;

    private Integer duration;

    private String level;

    private String language;

    private String requirements;

    private String learningOutcomes;

    private Boolean published;

    private Boolean active;

    private Double rating;

    private Integer totalStudents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
    private String instructorProfileImage;

}