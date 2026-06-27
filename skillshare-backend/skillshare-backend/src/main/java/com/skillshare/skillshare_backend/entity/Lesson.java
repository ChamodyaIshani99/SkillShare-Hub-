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
@Document(collection = "lessons")
public class Lesson {

    @Id
    private String id;

    private String title;

    private String description;

    // Lesson order (1,2,3...)
    private Integer lessonNumber;

    // YouTube / Vimeo / Cloudinary URL
    private String videoUrl;

    // PDF notes URL
    private String pdfUrl;

    // Duration in minutes
    private Integer duration;

    // Free preview lesson
    @Builder.Default
    private Boolean preview = false;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @DBRef
    private Course course;

}