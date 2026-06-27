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
@Document(collection = "progress")
public class Progress {

    @Id
    private String id;

    @DBRef
    private User student;

    @DBRef
    private Course course;

    @DBRef
    private Lesson lesson;

    @Builder.Default
    private Boolean completed = false;

    // Watch time in seconds
    @Builder.Default
    private Integer watchTime = 0;

    // 0 - 100
    @Builder.Default
    private Integer completionPercentage = 0;

    @Builder.Default
    private LocalDateTime lastAccessed = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

}