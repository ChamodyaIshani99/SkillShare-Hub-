package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.EnrollmentRequest;
import com.skillshare.skillshare_backend.dto.response.EnrollmentResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Enrollment;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.EnrollmentRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("You are already enrolled in this course.");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .completed(false)
                .progress(0)
                .active(true)
                .build();

        enrollment = enrollmentRepository.save(enrollment);

        course.setTotalStudents(
                (int) enrollmentRepository.countByCourse(course)
        );

        courseRepository.save(course);

        return mapToResponse(enrollment);
    }

    @Override
    public void cancelEnrollment(String enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!enrollment.getStudent().getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot cancel this enrollment.");
        }

        enrollmentRepository.delete(enrollment);

        Course course = enrollment.getCourse();

        course.setTotalStudents(
                (int) enrollmentRepository.countByCourse(course)
        );

        courseRepository.save(course);
    }
        @Override
    public List<EnrollmentResponse> getMyEnrollments() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return enrollmentRepository.findByStudentAndActiveTrue(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EnrollmentResponse getById(String enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUser = authentication.getName();

        boolean isStudent = enrollment.getStudent()
                .getEmail()
                .equals(currentUser);

        boolean isInstructor = enrollment.getCourse()
                .getInstructor()
                .getEmail()
                .equals(currentUser);

        if (!isStudent && !isInstructor) {
            throw new RuntimeException("You are not authorized to view this enrollment.");
        }

        return mapToResponse(enrollment);
    }

    @Override
    public EnrollmentResponse updateProgress(String enrollmentId, Integer progress) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!enrollment.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot update this enrollment.");
        }

        if (progress < 0 || progress > 100) {
            throw new RuntimeException("Progress must be between 0 and 100.");
        }

        enrollment.setProgress(progress);

        if (progress == 100) {
            enrollment.setCompleted(true);
        }

        enrollment = enrollmentRepository.save(enrollment);

        return mapToResponse(enrollment);
    }

    @Override
    public EnrollmentResponse markAsCompleted(String enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!enrollment.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot complete this course.");
        }

        enrollment.setCompleted(true);
        enrollment.setProgress(100);

        enrollment = enrollmentRepository.save(enrollment);

        return mapToResponse(enrollment);
    }
        @Override
    public List<EnrollmentResponse> getEnrollmentsByCourse(String courseId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not allowed to view these enrollments.");
        }

        return enrollmentRepository.findByCourseAndActiveTrue(course)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {

        return enrollmentRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {

        Course course = enrollment.getCourse();

        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .completed(enrollment.getCompleted())
                .progress(enrollment.getProgress())
                .active(enrollment.getActive())

                // Student
                .studentId(enrollment.getStudent().getId())
                .studentName(
                        enrollment.getStudent().getFirstName() + " " +
                        enrollment.getStudent().getLastName()
                )
                .studentEmail(enrollment.getStudent().getEmail())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseDescription(course.getDescription())
                .courseThumbnail(course.getThumbnail())
                .coursePrice(course.getPrice())
                .courseDuration(course.getDuration())
                .courseLevel(course.getLevel())
                .courseLanguage(course.getLanguage())

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
                .instructorProfileImage(course.getInstructor().getProfileImage())

                .build();
    }

}