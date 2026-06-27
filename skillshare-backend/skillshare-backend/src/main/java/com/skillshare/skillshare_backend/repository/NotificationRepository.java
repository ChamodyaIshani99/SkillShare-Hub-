package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Notification;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.enums.NotificationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    // ==========================================
    // User
    // ==========================================

    List<Notification> findByUser(User user);

    List<Notification> findByUser_Id(String userId);

    // ==========================================
    // Read / Unread
    // ==========================================

    List<Notification> findByUserAndReadFalse(User user);

    List<Notification> findByUserAndReadTrue(User user);

    long countByUserAndReadFalse(User user);

    // ==========================================
    // Notification Type
    // ==========================================

    List<Notification> findByNotificationType(
            NotificationType notificationType
    );

    List<Notification> findByUserAndNotificationType(
            User user,
            NotificationType notificationType
    );

    // ==========================================
    // Read Status + Type
    // ==========================================

    List<Notification> findByUserAndReadFalseAndNotificationType(
            User user,
            NotificationType notificationType
    );

    // ==========================================
    // Delete
    // ==========================================

    void deleteByUser(User user);

    void deleteByUserAndReadTrue(User user);

}