package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.response.CourseAnalyticsResponse;
import com.skillshare.skillshare_backend.dto.response.DashboardResponse;
import com.skillshare.skillshare_backend.dto.response.RevenueAnalyticsResponse;
import com.skillshare.skillshare_backend.dto.response.UserAnalyticsResponse;

public interface DashboardService {

    // ==========================================
    // Admin Dashboard
    // ==========================================

    DashboardResponse getAdminDashboard();

    // ==========================================
    // Instructor Dashboard
    // ==========================================

    DashboardResponse getInstructorDashboard();

    // ==========================================
    // Student Dashboard
    // ==========================================

    DashboardResponse getStudentDashboard();

    // ==========================================
    // Revenue Analytics
    // ==========================================

    RevenueAnalyticsResponse getRevenueAnalytics();

    // ==========================================
    // Course Analytics
    // ==========================================

    CourseAnalyticsResponse getCourseAnalytics();

    // ==========================================
    // User Analytics
    // ==========================================

    UserAnalyticsResponse getUserAnalytics();

}
