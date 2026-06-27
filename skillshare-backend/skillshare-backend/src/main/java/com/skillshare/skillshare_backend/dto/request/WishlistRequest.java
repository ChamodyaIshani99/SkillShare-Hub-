package com.skillshare.skillshare_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WishlistRequest {

    @NotBlank(message = "Course ID is required")
    private String courseId;

}