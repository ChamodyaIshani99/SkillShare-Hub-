package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Review;
import com.skillshare.skillshare_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    // ==========================================
    // Student
    // ==========================================

    List<Review> findByStudent(User student);

    List<Review> findByStudent_Id(String studentId);

    // ==========================================
    // Course
    // ==========================================

    List<Review> findByCourse(Course course);

    List<Review> findByCourse_Id(String courseId);

    // ==========================================
    // Student + Course
    // ==========================================

    Optional<Review> findByStudentAndCourse(
            User student,
            Course course
    );

    boolean existsByStudentAndCourse(
            User student,
            Course course
    );

    // ==========================================
    // Rating
    // ==========================================

    List<Review> findByRating(Integer rating);

    List<Review> findByCourse_IdAndRating(
            String courseId,
            Integer rating
    );

    // ==========================================
    // Statistics
    // ==========================================

    long countByCourse(Course course);

    long countByCourse_Id(String courseId);

    long countByStudent(User student);

    long countByRating(Integer rating);

}