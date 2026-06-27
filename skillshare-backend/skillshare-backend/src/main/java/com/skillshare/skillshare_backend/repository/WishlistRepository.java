package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    // ==========================================
    // Student
    // ==========================================

    List<Wishlist> findByStudent(User student);

    List<Wishlist> findByStudent_Id(String studentId);

    // ==========================================
    // Course
    // ==========================================

    List<Wishlist> findByCourse(Course course);

    List<Wishlist> findByCourse_Id(String courseId);

    // ==========================================
    // Student + Course
    // ==========================================

    Optional<Wishlist> findByStudentAndCourse(
            User student,
            Course course
    );

    boolean existsByStudentAndCourse(
            User student,
            Course course
    );

    void deleteByStudentAndCourse(
            User student,
            Course course
    );

    // ==========================================
    // Statistics
    // ==========================================

    long countByStudent(User student);

    long countByCourse(Course course);

    long countByCourse_Id(String courseId);

}