package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.CourseRequest;
import com.skillshare.skillshare_backend.dto.response.CourseResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Skill;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.SkillRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    @Override
    public CourseResponse create(CourseRequest request) {

        if (courseRepository.existsByTitle(request.getTitle())) {
            throw new RuntimeException("Course title already exists.");
        }

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User instructor = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .price(request.getPrice())
                .duration(request.getDuration())
                .level(request.getLevel())
                .language(request.getLanguage())
                .requirements(request.getRequirements())
                .learningOutcomes(request.getLearningOutcomes())
                .published(false)
                .active(true)
                .skill(skill)
                .instructor(instructor)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse update(String id, CourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!course.getInstructor().getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot update this course.");
        }

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setThumbnail(request.getThumbnail());
        course.setPrice(request.getPrice());
        course.setDuration(request.getDuration());
        course.setLevel(request.getLevel());
        course.setLanguage(request.getLanguage());
        course.setRequirements(request.getRequirements());
        course.setLearningOutcomes(request.getLearningOutcomes());
        course.setSkill(skill);
        course.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(courseRepository.save(course));
    }
        @Override
    public void delete(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!course.getInstructor().getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot delete this course.");
        }

        courseRepository.delete(course);
    }

    @Override
    public CourseResponse publish(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!course.getInstructor().getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot publish this course.");
        }

        course.setPublished(true);
        course.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse unpublish(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!course.getInstructor().getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot unpublish this course.");
        }

        course.setPublished(false);
        course.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(courseRepository.save(course));
    }

    @Override
    public List<CourseResponse> getMyCourses() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User instructor = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        return courseRepository.findByInstructor(instructor)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
        @Override
    public List<CourseResponse> getAllPublishedCourses() {

        return courseRepository.findByPublishedTrueAndActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CourseResponse getById(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getPublished()) {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                    authentication.getName().equals("anonymousUser") ||
                    !course.getInstructor().getEmail().equals(authentication.getName())) {

                throw new RuntimeException("Course is not published.");
            }
        }

        return mapToResponse(course);
    }

    @Override
    public List<CourseResponse> getBySkill(String skillId) {

        return courseRepository.findBySkill_Id(skillId)
                .stream()
                .filter(Course::getPublished)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> search(String keyword) {

        return courseRepository
                .findByTitleContainingIgnoreCaseAndPublishedTrueAndActiveTrue(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getAll() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CourseResponse mapToResponse(Course course) {

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .thumbnail(course.getThumbnail())
                .price(course.getPrice())
                .duration(course.getDuration())
                .level(course.getLevel())
                .language(course.getLanguage())
                .requirements(course.getRequirements())
                .learningOutcomes(course.getLearningOutcomes())
                .published(course.getPublished())
                .active(course.getActive())
                .rating(course.getRating())
                .totalStudents(course.getTotalStudents())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())

                .skillId(course.getSkill().getId())
                .skillTitle(course.getSkill().getTitle())

                .categoryId(course.getSkill().getCategory().getId())
                .categoryName(course.getSkill().getCategory().getName())

                .instructorId(course.getInstructor().getId())
                .instructorName(
                        course.getInstructor().getFirstName() + " " +
                        course.getInstructor().getLastName()
                )
                .instructorEmail(course.getInstructor().getEmail())
                .instructorProfileImage(course.getInstructor().getProfileImage())

                .build();
    }

}