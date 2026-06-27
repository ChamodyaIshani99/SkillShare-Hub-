package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.ProgressRequest;
import com.skillshare.skillshare_backend.dto.response.ProgressResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Lesson;
import com.skillshare.skillshare_backend.entity.Progress;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.LessonRepository;
import com.skillshare.skillshare_backend.repository.ProgressRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Override
    public ProgressResponse create(ProgressRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        if (progressRepository.existsByStudentAndLesson(student, lesson)) {
            throw new RuntimeException("Progress already exists for this lesson.");
        }

        Progress progress = Progress.builder()
                .student(student)
                .course(course)
                .lesson(lesson)
                .watchTime(request.getWatchTime())
                .completionPercentage(request.getCompletionPercentage())
                .completed(request.getCompleted())
                .lastAccessed(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        progress = progressRepository.save(progress);

        return mapToResponse(progress);
    }

    @Override
    public ProgressResponse update(String progressId, ProgressRequest request) {

        Progress progress = progressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Progress not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!progress.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not allowed to update this progress.");
        }

        progress.setWatchTime(request.getWatchTime());
        progress.setCompletionPercentage(request.getCompletionPercentage());
        progress.setCompleted(request.getCompleted());
        progress.setLastAccessed(LocalDateTime.now());
        progress.setUpdatedAt(LocalDateTime.now());

        progress = progressRepository.save(progress);

        return mapToResponse(progress);
    }
        @Override
    public ProgressResponse getById(String progressId) {

        Progress progress = progressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Progress not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUser = authentication.getName();

        boolean isStudent = progress.getStudent()
                .getEmail()
                .equals(currentUser);

        boolean isInstructor = progress.getCourse()
                .getInstructor()
                .getEmail()
                .equals(currentUser);

        if (!isStudent && !isInstructor) {
            throw new RuntimeException("You are not authorized to view this progress.");
        }

        return mapToResponse(progress);
    }

    @Override
    public List<ProgressResponse> getMyProgressByCourse(String courseId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return progressRepository.findByStudentAndCourse(student, course)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProgressResponse> getMyProgress() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return progressRepository.findByStudent(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

        @Override
    public List<ProgressResponse> getCourseProgress(String courseId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not authorized to view this course progress.");
        }

        return progressRepository.findByCourse(course)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProgressResponse> getAllProgress() {

        return progressRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProgressResponse mapToResponse(Progress progress) {

        Course course = progress.getCourse();
        Lesson lesson = progress.getLesson();

        return ProgressResponse.builder()
                .id(progress.getId())
                .completed(progress.getCompleted())
                .watchTime(progress.getWatchTime())
                .completionPercentage(progress.getCompletionPercentage())
                .lastAccessed(progress.getLastAccessed())
                .createdAt(progress.getCreatedAt())
                .updatedAt(progress.getUpdatedAt())

                // Student
                .studentId(progress.getStudent().getId())
                .studentName(
                        progress.getStudent().getFirstName() + " " +
                        progress.getStudent().getLastName()
                )
                .studentEmail(progress.getStudent().getEmail())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseThumbnail(course.getThumbnail())

                // Total lessons in course
                .totalLessons(
                        lessonRepository.findByCourse_IdOrderByLessonNumberAsc(course.getId()).size()
                )

                // Lesson
                .lessonId(lesson.getId())
                .lessonTitle(lesson.getTitle())
                .lessonNumber(lesson.getLessonNumber())
                .lessonDuration(lesson.getDuration())
                .preview(lesson.getPreview())

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

                .build();
    }

}