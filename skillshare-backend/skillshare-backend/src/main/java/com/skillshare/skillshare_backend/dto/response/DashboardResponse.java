package com.skillshare.skillshare_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    // Users
    private Long totalUsers;
    private Long totalStudents;
    private Long totalInstructors;

    // Learning
    private Long totalCourses;
    private Long totalLessons;
    private Long totalEnrollments;

    // Finance
    private Long totalPayments;
    private Double totalRevenue;

    // Reviews
    private Long totalReviews;
    private Double averageRating;

    // Certificates
    private Long totalCertificates;

    // Wishlist
    private Long totalWishlist;

}