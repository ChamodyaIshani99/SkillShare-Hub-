package com.skillshare.skillshare_backend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String thumbnail;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private Double price;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 hour")
    private Integer duration;

    @NotBlank(message = "Level is required")
    private String level;

    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Requirements are required")
    private String requirements;

    @NotBlank(message = "Learning outcomes are required")
    private String learningOutcomes;

    @NotBlank(message = "Skill ID is required")
    private String skillId;

}