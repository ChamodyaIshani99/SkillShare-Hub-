package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.service.interfaces.EmailService;
import com.skillshare.skillshare_backend.util.EmailTemplateUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendWelcomeEmail(
            String to,
            String firstName
    ) {

        sendEmail(
                to,
                "Welcome to SkillShare",
                EmailTemplateUtil.welcomeTemplate(firstName)
        );
    }

    @Override
    @Async
    public void sendVerificationEmail(
            String to,
            String firstName,
            String verificationLink
    ) {

        sendEmail(
                to,
                "Verify Your Email",
                EmailTemplateUtil.verificationTemplate(
                        firstName,
                        verificationLink
                )
        );
    }

    @Override
    @Async
    public void sendPasswordResetEmail(
            String to,
            String firstName,
            String resetLink
    ) {

        sendEmail(
                to,
                "Reset Your Password",
                EmailTemplateUtil.passwordResetTemplate(
                        firstName,
                        resetLink
                )
        );
    }

    @Override
    @Async
    public void sendEnrollmentConfirmation(
            String to,
            String studentName,
            String courseTitle
    ) {

        sendEmail(
                to,
                "Course Enrollment Confirmation",
                EmailTemplateUtil.enrollmentTemplate(
                        studentName,
                        courseTitle
                )
        );
    }

    @Override
    @Async
    public void sendPaymentReceipt(
            String to,
            String studentName,
            String courseTitle,
            Double amount,
            String transactionId
    ) {

        sendEmail(
                to,
                "Payment Successful",
                EmailTemplateUtil.paymentReceiptTemplate(
                        studentName,
                        courseTitle,
                        amount,
                        transactionId
                )
        );
    }

    @Override
    @Async
    public void sendCertificateEmail(
            String to,
            String studentName,
            String courseTitle,
            String certificateUrl
    ) {

        sendEmail(
                to,
                "Your Course Certificate",
                EmailTemplateUtil.certificateTemplate(
                        studentName,
                        courseTitle,
                        certificateUrl
                )
        );
    }

    @Override
    @Async
    public void sendEmail(
            String to,
            String subject,
            String body
    ) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Failed to send email.",
                    e
            );
        }
    }

}