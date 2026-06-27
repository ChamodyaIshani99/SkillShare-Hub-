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
@Document(collection = "enrollments")
public class Enrollment {

    @Id
    private String id;

    @DBRef
    private User student;

    @DBRef
    private Course course;

    @Builder.Default
    private LocalDateTime enrollmentDate = LocalDateTime.now();

    @Builder.Default
    private Boolean completed = false;

    // Percentage (0 - 100)
    @Builder.Default
    private Integer progress = 0;

    @Builder.Default
    private Boolean active = true;

}