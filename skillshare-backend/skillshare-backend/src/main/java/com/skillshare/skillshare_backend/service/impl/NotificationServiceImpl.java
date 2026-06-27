package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.response.NotificationResponse;
import com.skillshare.skillshare_backend.entity.Notification;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.enums.NotificationType;
import com.skillshare.skillshare_backend.repository.NotificationRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public NotificationResponse sendNotification(
            String userId,
            String title,
            String message,
            NotificationType notificationType
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .notificationType(notificationType)
                .read(false)
                .build();

        notification = notificationRepository.save(notification);

        return mapToResponse(notification);
    }

    @Override
    public List<NotificationResponse> getMyNotifications() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
        @Override
    public List<NotificationResponse> getUnreadNotifications() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUserAndReadFalse(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NotificationResponse markAsRead(String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!notification.getUser()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException(
                    "You are not authorized to update this notification."
            );
        }

        notification.setRead(true);

        notification = notificationRepository.save(notification);

        return mapToResponse(notification);
    }

    @Override
    public void markAllAsRead() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Notification> notifications =
                notificationRepository.findByUserAndReadFalse(user);

        notifications.forEach(notification -> notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }
        @Override
    public void deleteNotification(String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!notification.getUser()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException(
                    "You are not authorized to delete this notification."
            );
        }

        notificationRepository.delete(notification);
    }

    @Override
    public long getUnreadCount() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.countByUserAndReadFalse(user);
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private NotificationResponse mapToResponse(Notification notification) {

        User user = notification.getUser();

        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .read(notification.getRead())
                .createdAt(notification.getCreatedAt())

                // User Details
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())

                .build();
    }

}
