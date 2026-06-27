package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.response.NotificationResponse;
import com.skillshare.skillshare_backend.enums.NotificationType;

import java.util.List;

public interface NotificationService {

    // ==========================================
    // User APIs
    // ==========================================

    // Get all notifications of the logged-in user
    List<NotificationResponse> getMyNotifications();

    // Get unread notifications
    List<NotificationResponse> getUnreadNotifications();

    // Mark a notification as read
    NotificationResponse markAsRead(String notificationId);

    // Mark all notifications as read
    void markAllAsRead();

    // Delete a notification
    void deleteNotification(String notificationId);

    // Get unread notification count
    long getUnreadCount();

    // ==========================================
    // System/Admin APIs
    // ==========================================

    // Send a notification to a user
    NotificationResponse sendNotification(
            String userId,
            String title,
            String message,
            NotificationType notificationType
    );

    // Get all notifications
    List<NotificationResponse> getAllNotifications();

}