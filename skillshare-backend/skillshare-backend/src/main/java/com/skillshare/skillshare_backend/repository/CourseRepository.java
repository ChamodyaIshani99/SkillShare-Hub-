package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Skill;
import com.skillshare.skillshare_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    // Get all active courses
    List<Course> findByActiveTrue();

    // Get all published courses
    List<Course> findByPublishedTrue();

    // Get published & active courses
    List<Course> findByPublishedTrueAndActiveTrue();

    // Get courses by skill
    List<Course> findBySkill(Skill skill);

    // Get courses by skill id
    List<Course> findBySkill_Id(String skillId);

    // Get instructor's courses
    List<Course> findByInstructor(User instructor);

    // Get instructor's courses by id
    List<Course> findByInstructor_Id(String instructorId);

    // Get only published courses of an instructor
    List<Course> findByInstructor_IdAndPublishedTrue(String instructorId);

    // Search by title
    List<Course> findByTitleContainingIgnoreCase(String keyword);

    // Search by title (published only)
    List<Course> findByTitleContainingIgnoreCaseAndPublishedTrue(String keyword);

    // Search by title (published & active)
    List<Course> findByTitleContainingIgnoreCaseAndPublishedTrueAndActiveTrue(String keyword);

    // Check duplicate title
    boolean existsByTitle(String title);

}