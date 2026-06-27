package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.PaymentRequest;
import com.skillshare.skillshare_backend.dto.response.PaymentResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Enrollment;
import com.skillshare.skillshare_backend.entity.Payment;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.enums.PaymentStatus;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.EnrollmentRepository;
import com.skillshare.skillshare_backend.repository.PaymentRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (paymentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("You have already purchased this course.");
        }

        Payment payment = Payment.builder()
                .student(student)
                .course(course)
                .amount(course.getPrice())
                .currency("LKR")
                .paymentMethod(request.getPaymentMethod())
                .transactionId(generateTransactionId())
                .paymentStatus(PaymentStatus.SUCCESS)
                .paymentDate(LocalDateTime.now())
                .build();

        payment = paymentRepository.save(payment);

        // Automatically enroll the student after successful payment
        if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {

            Enrollment enrollment = Enrollment.builder()
                    .student(student)
                    .course(course)
                    .completed(false)
                    .progress(0)
                    .active(true)
                    .build();

            enrollmentRepository.save(enrollment);

            course.setTotalStudents(
                    (int) enrollmentRepository.countByCourse(course)
            );

            courseRepository.save(course);
        }

        return mapToResponse(payment);
    }
        @Override
    public List<PaymentResponse> getMyPayments() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return paymentRepository.findByStudent(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PaymentResponse getById(String paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUser = authentication.getName();

        boolean isStudent = payment.getStudent()
                .getEmail()
                .equals(currentUser);

        boolean isInstructor = payment.getCourse()
                .getInstructor()
                .getEmail()
                .equals(currentUser);

        if (!isStudent && !isInstructor) {
            throw new RuntimeException("You are not authorized to view this payment.");
        }

        return mapToResponse(payment);
    }
        @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PaymentResponse updatePaymentStatus(
            String paymentId,
            PaymentStatus status
    ) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus(status);

        payment = paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse refundPayment(String paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.REFUNDED);

        payment = paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        Course course = payment.getCourse();

        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getPaymentDate())

                // Student
                .studentId(payment.getStudent().getId())
                .studentName(
                        payment.getStudent().getFirstName() + " " +
                        payment.getStudent().getLastName()
                )
                .studentEmail(payment.getStudent().getEmail())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseDescription(course.getDescription())
                .courseThumbnail(course.getThumbnail())
                .coursePrice(course.getPrice())
                .courseRating(course.getRating())
                .totalStudents(course.getTotalStudents())

                // Skill
                .skillId(course.getSkill().getId())
                .skillTitle(course.getSkill().getTitle())

                // Category
                .categoryId(course.getSkill().getCategory().getId())
                .categoryName(course.getSkill().getCategory().getName())

                // Instructor
                .instructorId(course.getInstructor().getId())
                .instructorName(
                        course.getInstructor().getFirstName() + " " +
                        course.getInstructor().getLastName()
                )
                .instructorEmail(course.getInstructor().getEmail())
                .instructorProfileImage(course.getInstructor().getProfileImage())

                .build();
    }

    /**
     * Generate a unique transaction ID.
     * Example: TXN-2026-8F3A2C7D
     */
    private String generateTransactionId() {

        String transactionId;

        do {

            transactionId = "TXN-"
                    + Year.now().getValue()
                    + "-"
                    + UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();

        } while (paymentRepository.existsByTransactionId(transactionId));

        return transactionId;
    }

}