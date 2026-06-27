package com.skillshare.skillshare_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    private String title;

    private String description;

    private String thumbnail;

    private Double price;

    // Duration in hours
    private Integer duration;

    // Beginner / Intermediate / Advanced
    private String level;

    // English / Sinhala / Tamil
    private String language;

    private String requirements;

    private String learningOutcomes;

    @Builder.Default
    private Boolean published = false;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Double rating = 0.0;

    @Builder.Default
    private Integer totalStudents = 0;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @DBRef
    private Skill skill;

    @DBRef
    private User instructor;
}