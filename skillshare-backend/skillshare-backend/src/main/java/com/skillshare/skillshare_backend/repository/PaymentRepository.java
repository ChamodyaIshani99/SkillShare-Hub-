package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Payment;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.enums.PaymentMethod;
import com.skillshare.skillshare_backend.enums.PaymentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    // ==========================================
    // Student
    // ==========================================

    List<Payment> findByStudent(User student);

    List<Payment> findByStudent_Id(String studentId);

    // ==========================================
    // Course
    // ==========================================

    List<Payment> findByCourse(Course course);

    List<Payment> findByCourse_Id(String courseId);

    // ==========================================
    // Student + Course
    // ==========================================

    Optional<Payment> findByStudentAndCourse(
            User student,
            Course course
    );

    boolean existsByStudentAndCourse(
            User student,
            Course course
    );

    // ==========================================
    // Transaction
    // ==========================================

    Optional<Payment> findByTransactionId(
            String transactionId
    );

    boolean existsByTransactionId(
            String transactionId
    );

    // ==========================================
    // Payment Status
    // ==========================================

    List<Payment> findByPaymentStatus(
            PaymentStatus paymentStatus
    );

    List<Payment> findByStudentAndPaymentStatus(
            User student,
            PaymentStatus paymentStatus
    );

    List<Payment> findByCourseAndPaymentStatus(
            Course course,
            PaymentStatus paymentStatus
    );

    // ==========================================
    // Payment Method
    // ==========================================

    List<Payment> findByPaymentMethod(
            PaymentMethod paymentMethod
    );

    // ==========================================
    // Statistics
    // ==========================================

    long countByStudent(User student);

    long countByCourse(Course course);

    long countByPaymentStatus(
            PaymentStatus paymentStatus
    );

    long countByPaymentMethod(
            PaymentMethod paymentMethod
    );

}