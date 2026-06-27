package com.skillshare.skillshare_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAnalyticsResponse {

    // ==========================================
    // Course Statistics
    // ==========================================

    private Long totalCourses;

    private Long activeCourses;

    private Long inactiveCourses;

    // ==========================================
    // Enrollments
    // ==========================================

    private Long totalEnrollments;

    private Long completedEnrollments;

    private Long activeEnrollments;

    // ==========================================
    // Ratings
    // ==========================================

    private Double averageCourseRating;

    private Double highestCourseRating;

    // ==========================================
    // Top Course
    // ==========================================

    private String topRatedCourse;

    private String mostEnrolledCourse;

    private String mostWishlistedCourse;

    private String highestRevenueCourse;

    // ==========================================
    // Revenue
    // ==========================================

    private Double totalRevenue;

    private Double averageCourseRevenue;

}