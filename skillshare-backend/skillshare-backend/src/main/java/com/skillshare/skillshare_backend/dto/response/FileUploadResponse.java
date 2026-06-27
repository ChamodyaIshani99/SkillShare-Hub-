package com.skillshare.skillshare_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {

    // Cloudinary Public ID
    private String publicId;

    // Original File Name
    private String originalFileName;

    // Uploaded File URL
    private String fileUrl;

    // File Format (jpg, png, mp4, pdf...)
    private String fileFormat;

    // Resource Type (image, video, raw)
    private String resourceType;

    // File Size (bytes)
    private Long fileSize;

    // Width (images/videos)
    private Integer width;

    // Height (images/videos)
    private Integer height;

    // Upload Time
    private LocalDateTime uploadedAt;

}
