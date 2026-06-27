package com.skillshare.skillshare_backend.dto.response;

import com.skillshare.skillshare_backend.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private String id;

    private String title;

    private String message;

    private NotificationType notificationType;

    private Boolean read;

    private LocalDateTime createdAt;

    // ==========================
    // User Details
    // ==========================

    private String userId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String profileImage;

}