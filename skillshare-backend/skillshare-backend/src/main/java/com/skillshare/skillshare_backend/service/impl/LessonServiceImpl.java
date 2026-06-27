package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.LessonRequest;
import com.skillshare.skillshare_backend.dto.response.LessonResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Lesson;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.LessonRepository;
import com.skillshare.skillshare_backend.service.interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public LessonResponse create(LessonRequest request) {

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!course.getInstructor().getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not allowed to add lessons to this course.");
        }

        if (lessonRepository.existsByCourseAndLessonNumber(
                course,
                request.getLessonNumber())) {

            throw new RuntimeException("Lesson number already exists.");
        }

        if (lessonRepository.existsByCourseAndTitle(
                course,
                request.getTitle())) {

            throw new RuntimeException("Lesson title already exists.");
        }

        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .lessonNumber(request.getLessonNumber())
                .videoUrl(request.getVideoUrl())
                .pdfUrl(request.getPdfUrl())
                .duration(request.getDuration())
                .preview(request.getPreview())
                .active(true)
                .course(course)
                .createdAt(LocalDateTime.now())
                .build();

        lesson = lessonRepository.save(lesson);

        return mapToResponse(lesson);
    }

    @Override
    public LessonResponse update(String id, LessonRequest request) {

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!lesson.getCourse()
                .getInstructor()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not allowed to update this lesson.");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setLessonNumber(request.getLessonNumber());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setPdfUrl(request.getPdfUrl());
        lesson.setDuration(request.getDuration());
        lesson.setPreview(request.getPreview());
        lesson.setCourse(course);
        lesson.setUpdatedAt(LocalDateTime.now());

        lesson = lessonRepository.save(lesson);

        return mapToResponse(lesson);
    }
        @Override
    public void delete(String id) {

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!lesson.getCourse()
                .getInstructor()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not allowed to delete this lesson.");
        }

        lessonRepository.delete(lesson);
    }

    @Override
    public LessonResponse getById(String id) {

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        return mapToResponse(lesson);
    }

    @Override
    public List<LessonResponse> getByCourse(String courseId) {

        return lessonRepository
                .findByCourse_IdAndActiveTrueOrderByLessonNumberAsc(courseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

        @Override
    public List<LessonResponse> getPreviewLessons(String courseId) {

        return lessonRepository
                .findByCourse_IdAndPreviewTrueAndActiveTrueOrderByLessonNumberAsc(courseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LessonResponse> getAll() {

        return lessonRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private LessonResponse mapToResponse(Lesson lesson) {

        Course course = lesson.getCourse();

        return LessonResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .lessonNumber(lesson.getLessonNumber())
                .videoUrl(lesson.getVideoUrl())
                .pdfUrl(lesson.getPdfUrl())
                .duration(lesson.getDuration())
                .preview(lesson.getPreview())
                .active(lesson.getActive())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())

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