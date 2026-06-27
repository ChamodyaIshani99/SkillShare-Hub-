package com.skillshare.skillshare_backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonRequest {

    @NotBlank(message = "Lesson title is required")
    private String title;

    @NotBlank(message = "Lesson description is required")
    private String description;

    @NotNull(message = "Lesson number is required")
    @Min(value = 1, message = "Lesson number must be at least 1")
    private Integer lessonNumber;

    @NotBlank(message = "Video URL is required")
    private String videoUrl;

    private String pdfUrl;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    // Free preview lesson
    private Boolean preview = false;

    @NotBlank(message = "Course ID is required")
    private String courseId;

}