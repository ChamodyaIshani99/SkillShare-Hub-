package com.skillshare.skillshare_backend.dto.response;

import com.skillshare.skillshare_backend.enums.PaymentMethod;
import com.skillshare.skillshare_backend.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String id;

    private Double amount;

    private String currency;

    private PaymentMethod paymentMethod;

    private String transactionId;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

    // ==========================
    // Student Details
    // ==========================

    private String studentId;

    private String studentName;

    private String studentEmail;

    // ==========================
    // Course Details
    // ==========================

    private String courseId;

    private String courseTitle;

    private String courseDescription;

    private String courseThumbnail;

    private Double coursePrice;

    private Double courseRating;

    private Integer totalStudents;

    // ==========================
    // Skill Details
    // ==========================

    private String skillId;

    private String skillTitle;

    // ==========================
    // Category Details
    // ==========================

    private String categoryId;

    private String categoryName;

    // ==========================
    // Instructor Details
    // ==========================

    private String instructorId;

    private String instructorName;

    private String instructorEmail;

    private String instructorProfileImage;

}