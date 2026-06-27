package com.skillshare.skillshare_backend.util;

public class EmailTemplateUtil {

    private EmailTemplateUtil() {
    }

    // ==========================================
    // Welcome Email
    // ==========================================

    public static String welcomeTemplate(String firstName) {

        return """
                <html>
                <body>

                <h2>Welcome %s!</h2>

                <p>
                Your SkillShare account has been created successfully.
                </p>

                <p>
                We are excited to have you with us.
                </p>

                <br>

                <p>
                Happy Learning!
                </p>

                <b>SkillShare Team</b>

                </body>
                </html>
                """.formatted(firstName);
    }

    // ==========================================
    // Email Verification
    // ==========================================

    public static String verificationTemplate(
            String firstName,
            String verificationLink
    ) {

        return """
                <html>
                <body>

                <h2>Hello %s</h2>

                <p>
                Please verify your email by clicking the link below.
                </p>

                <a href="%s">
                    Verify Email
                </a>

                <br><br>

                <p>
                Thank you.
                </p>

                <b>SkillShare Team</b>

                </body>
                </html>
                """.formatted(firstName, verificationLink);
    }

    // ==========================================
    // Password Reset
    // ==========================================

    public static String passwordResetTemplate(
            String firstName,
            String resetLink
    ) {

        return """
                <html>
                <body>

                <h2>Hello %s</h2>

                <p>
                Click the button below to reset your password.
                </p>

                <a href="%s">
                    Reset Password
                </a>

                <br><br>

                <p>
                If you didn't request this, simply ignore this email.
                </p>

                <b>SkillShare Team</b>

                </body>
                </html>
                """.formatted(firstName, resetLink);
    }

    // ==========================================
    // Enrollment
    // ==========================================

    public static String enrollmentTemplate(
            String studentName,
            String courseTitle
    ) {

        return """
                <html>
                <body>

                <h2>Hello %s</h2>

                <p>
                Congratulations!
                </p>

                <p>
                You have successfully enrolled in:
                </p>

                <h3>%s</h3>

                <p>
                Start learning today.
                </p>

                <br>

                <b>SkillShare Team</b>

                </body>
                </html>
                """.formatted(studentName, courseTitle);
    }

    // ==========================================
    // Payment Receipt
    // ==========================================

    public static String paymentReceiptTemplate(
            String studentName,
            String courseTitle,
            Double amount,
            String transactionId
    ) {

        return """
                <html>
                <body>

                <h2>Hello %s</h2>

                <p>
                Your payment has been completed successfully.
                </p>

                <table border="1" cellpadding="8">

                    <tr>
                        <td><b>Course</b></td>
                        <td>%s</td>
                    </tr>

                    <tr>
                        <td><b>Amount</b></td>
                        <td>LKR %.2f</td>
                    </tr>

                    <tr>
                        <td><b>Transaction ID</b></td>
                        <td>%s</td>
                    </tr>

                </table>

                <br>

                <b>
                Thank you for learning with SkillShare.
                </b>

                </body>
                </html>
                """.formatted(
                studentName,
                courseTitle,
                amount,
                transactionId
        );
    }

    // ==========================================
    // Certificate
    // ==========================================

    public static String certificateTemplate(
            String studentName,
            String courseTitle,
            String certificateUrl
    ) {

        return """
                <html>
                <body>

                <h2>Congratulations %s!</h2>

                <p>
                You have successfully completed:
                </p>

                <h3>%s</h3>

                <p>
                Your certificate is ready.
                </p>

                <a href="%s">
                    Download Certificate
                </a>

                <br><br>

                <b>Keep Learning!</b>

                <br>

                SkillShare Team

                </body>
                </html>
                """.formatted(
                studentName,
                courseTitle,
                certificateUrl
        );
    }

}