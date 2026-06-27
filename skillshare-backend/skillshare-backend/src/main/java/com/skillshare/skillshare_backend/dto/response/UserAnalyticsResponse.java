package com.skillshare.skillshare_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnalyticsResponse {

    // ==========================================
    // User Statistics
    // ==========================================

    private Long totalUsers;

    private Long totalStudents;

    private Long totalInstructors;

    private Long totalAdmins;

    // ==========================================
    // User Status
    // ==========================================

    private Long activeUsers;

    private Long inactiveUsers;

    private Long verifiedUsers;

    private Long unverifiedUsers;

    // ==========================================
    // Registration Statistics
    // ==========================================

    private Long todayRegistrations;

    private Long weeklyRegistrations;

    private Long monthlyRegistrations;

    private Long yearlyRegistrations;

    // ==========================================
    // Activity
    // ==========================================

    private String mostActiveStudent;

    private String mostActiveInstructor;

    // ==========================================
    // Performance
    // ==========================================

    private Double studentInstructorRatio;

}