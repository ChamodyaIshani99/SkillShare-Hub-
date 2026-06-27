package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Enrollment;
import com.skillshare.skillshare_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    // ==========================
    // Student
    // ==========================

    List<Enrollment> findByStudent(User student);

    List<Enrollment> findByStudent_Id(String studentId);

    List<Enrollment> findByStudentAndActiveTrue(User student);

    List<Enrollment> findByStudent_IdAndActiveTrue(String studentId);

    List<Enrollment> findByStudentAndCompletedTrue(User student);

    // ==========================
    // Course
    // ==========================

    List<Enrollment> findByCourse(Course course);

    List<Enrollment> findByCourse_Id(String courseId);

    List<Enrollment> findByCourseAndActiveTrue(Course course);

    List<Enrollment> findByCourse_IdAndActiveTrue(String courseId);

    // ==========================
    // Validation
    // ==========================

    boolean existsByStudentAndCourse(User student, Course course);

    Optional<Enrollment> findByStudentAndCourse(User student, Course course);

    // ==========================
    // Statistics
    // ==========================

    long countByCourse(Course course);

    long countByCourse_Id(String courseId);

    long countByStudent(User student);

    long countByCompletedTrue();

    long countByCompletedFalse();

    // ==========================
    // Admin
    // ==========================

    List<Enrollment> findByActiveTrue();

}