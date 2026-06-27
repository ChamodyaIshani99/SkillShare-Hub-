package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Lesson;
import com.skillshare.skillshare_backend.entity.Progress;
import com.skillshare.skillshare_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, String> {

    // ==========================================
    // Student
    // ==========================================

    List<Progress> findByStudent(User student);

    List<Progress> findByStudent_Id(String studentId);

    // ==========================================
    // Course
    // ==========================================

    List<Progress> findByCourse(Course course);

    List<Progress> findByCourse_Id(String courseId);

    // ==========================================
    // Lesson
    // ==========================================

    List<Progress> findByLesson(Lesson lesson);

    List<Progress> findByLesson_Id(String lessonId);

    // ==========================================
    // Student + Course
    // ==========================================

    List<Progress> findByStudentAndCourse(User student, Course course);

    List<Progress> findByStudent_IdAndCourse_Id(
            String studentId,
            String courseId
    );

    // ==========================================
    // Student + Lesson
    // ==========================================

    Optional<Progress> findByStudentAndLesson(
            User student,
            Lesson lesson
    );

    Optional<Progress> findByStudent_IdAndLesson_Id(
            String studentId,
            String lessonId
    );

    // ==========================================
    // Student + Course + Lesson
    // ==========================================

    Optional<Progress> findByStudentAndCourseAndLesson(
            User student,
            Course course,
            Lesson lesson
    );

    // ==========================================
    // Validation
    // ==========================================

    boolean existsByStudentAndLesson(
            User student,
            Lesson lesson
    );

    // ==========================================
    // Completed Lessons
    // ==========================================

    List<Progress> findByStudentAndCompletedTrue(
            User student
    );

    List<Progress> findByStudentAndCourseAndCompletedTrue(
            User student,
            Course course
    );

    // ==========================================
    // Statistics
    // ==========================================

    long countByStudent(User student);

    long countByCourse(Course course);

    long countByLesson(Lesson lesson);

    long countByStudentAndCourse(
            User student,
            Course course
    );

    long countByStudentAndCourseAndCompletedTrue(
            User student,
            Course course
    );

}