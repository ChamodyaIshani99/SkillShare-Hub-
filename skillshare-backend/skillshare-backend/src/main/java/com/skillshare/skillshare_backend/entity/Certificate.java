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
@Document(collection = "certificates")
public class Certificate {

    @Id
    private String id;

    // Unique certificate number
    private String certificateNumber;

    @DBRef
    private User student;

    @DBRef
    private Course course;

    @Builder.Default
    private LocalDateTime issueDate = LocalDateTime.now();

    private LocalDateTime completionDate;

    // PDF URL or Cloudinary URL
    private String certificateUrl;

    @Builder.Default
    private Boolean verified = true;

}