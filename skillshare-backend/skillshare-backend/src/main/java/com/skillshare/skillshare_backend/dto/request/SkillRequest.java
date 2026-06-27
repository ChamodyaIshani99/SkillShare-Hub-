package com.skillshare.skillshare_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SkillRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Level is required")
    private String level;

    private String image;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

}