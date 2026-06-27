package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Certificate;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends MongoRepository<Certificate, String> {

    // ==========================================
    // Student
    // ==========================================

    List<Certificate> findByStudent(User student);

    List<Certificate> findByStudent_Id(String studentId);

    // ==========================================
    // Course
    // ==========================================

    List<Certificate> findByCourse(Course course);

    List<Certificate> findByCourse_Id(String courseId);

    // ==========================================
    // Student + Course
    // ==========================================

    Optional<Certificate> findByStudentAndCourse(
            User student,
            Course course
    );

    boolean existsByStudentAndCourse(
            User student,
            Course course
    );

    // ==========================================
    // Certificate Number
    // ==========================================

    Optional<Certificate> findByCertificateNumber(
            String certificateNumber
    );

    boolean existsByCertificateNumber(
            String certificateNumber
    );

    // ==========================================
    // Verification
    // ==========================================

    List<Certificate> findByVerifiedTrue();

    List<Certificate> findByVerifiedFalse();

    // ==========================================
    // Statistics
    // ==========================================

    long countByStudent(User student);

    long countByCourse(Course course);

    long countByVerifiedTrue();

    long countByVerifiedFalse();

}