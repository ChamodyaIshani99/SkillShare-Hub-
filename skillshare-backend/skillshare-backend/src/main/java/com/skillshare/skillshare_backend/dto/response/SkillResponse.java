package com.skillshare.skillshare_backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillResponse {

    private String id;

    private String title;

    private String description;

    private String level;

    private String image;

    private String categoryId;

    private String categoryName;

    private String instructorId;

    private String instructorName;

}