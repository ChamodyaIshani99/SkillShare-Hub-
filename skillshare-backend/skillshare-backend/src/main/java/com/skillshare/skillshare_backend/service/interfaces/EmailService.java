package com.skillshare.skillshare_backend.service.interfaces;

public interface EmailService {

    // ==========================================
    // Authentication
    // ==========================================

    // Welcome email
    void sendWelcomeEmail(
            String to,
            String firstName
    );

    // Email verification
    void sendVerificationEmail(
            String to,
            String firstName,
            String verificationLink
    );

    // Forgot password
    void sendPasswordResetEmail(
            String to,
            String firstName,
            String resetLink
    );

    // ==========================================
    // Enrollment
    // ==========================================

    // Course enrollment confirmation
    void sendEnrollmentConfirmation(
            String to,
            String studentName,
            String courseTitle
    );

    // ==========================================
    // Payment
    // ==========================================

    // Payment receipt
    void sendPaymentReceipt(
            String to,
            String studentName,
            String courseTitle,
            Double amount,
            String transactionId
    );

    // ==========================================
    // Certificate
    // ==========================================

    // Certificate issued
    void sendCertificateEmail(
            String to,
            String studentName,
            String courseTitle,
            String certificateUrl
    );

    // ==========================================
    // Generic Email
    // ==========================================

    // Send custom email
    void sendEmail(
            String to,
            String subject,
            String body
    );

}