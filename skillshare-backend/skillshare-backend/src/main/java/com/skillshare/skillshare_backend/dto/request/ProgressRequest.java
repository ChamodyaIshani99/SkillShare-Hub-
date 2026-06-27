package com.skillshare.skillshare_backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgressRequest {

    @NotBlank(message = "Course ID is required")
    private String courseId;

    @NotBlank(message = "Lesson ID is required")
    private String lessonId;

    @NotNull(message = "Watch time is required")
    @Min(value = 0, message = "Watch time cannot be negative")
    private Integer watchTime;

    @NotNull(message = "Completion percentage is required")
    @Min(value = 0, message = "Completion percentage must be at least 0")
    @Max(value = 100, message = "Completion percentage cannot exceed 100")
    private Integer completionPercentage;

    private Boolean completed = false;

}