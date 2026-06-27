package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends MongoRepository<Lesson, String> {

    // Get all active lessons
    List<Lesson> findByActiveTrue();

    // Get all lessons of a course ordered by lesson number
    List<Lesson> findByCourseOrderByLessonNumberAsc(Course course);

    // Get all lessons by course id ordered by lesson number
    List<Lesson> findByCourse_IdOrderByLessonNumberAsc(String courseId);

    // Get preview lessons of a course
    List<Lesson> findByCourse_IdAndPreviewTrueOrderByLessonNumberAsc(String courseId);

    // Get active lessons of a course
    List<Lesson> findByCourse_IdAndActiveTrueOrderByLessonNumberAsc(String courseId);

    // Get active preview lessons
    List<Lesson> findByCourse_IdAndPreviewTrueAndActiveTrueOrderByLessonNumberAsc(String courseId);

    // Check duplicate lesson number within the same course
    boolean existsByCourseAndLessonNumber(Course course, Integer lessonNumber);

    // Check duplicate lesson title within the same course
    boolean existsByCourseAndTitle(Course course, String title);

    // Find lesson by course and lesson number
    Optional<Lesson> findByCourseAndLessonNumber(Course course, Integer lessonNumber);

}