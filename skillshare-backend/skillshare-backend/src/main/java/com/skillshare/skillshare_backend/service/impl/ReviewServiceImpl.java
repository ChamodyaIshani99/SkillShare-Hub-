package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.ReviewRequest;
import com.skillshare.skillshare_backend.dto.response.ReviewResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Review;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.EnrollmentRepository;
import com.skillshare.skillshare_backend.repository.ReviewRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public ReviewResponse create(ReviewRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Student must be enrolled
        if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("You must enroll before reviewing this course.");
        }

        // Only one review per student
        if (reviewRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("You have already reviewed this course.");
        }

        Review review = Review.builder()
                .student(student)
                .course(course)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        review = reviewRepository.save(review);

        updateCourseRating(course);

        return mapToResponse(review);
    }

    @Override
    public ReviewResponse update(String reviewId, ReviewRequest request) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!review.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot update this review.");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(LocalDateTime.now());

        review = reviewRepository.save(review);

        updateCourseRating(review.getCourse());

        return mapToResponse(review);
    }
        @Override
    public void delete(String reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!review.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot delete this review.");
        }

        Course course = review.getCourse();

        reviewRepository.delete(review);

        updateCourseRating(course);
    }

    @Override
    public List<ReviewResponse> getMyReviews() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return reviewRepository.findByStudent(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ReviewResponse getById(String reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return mapToResponse(review);
    }
        @Override
    public List<ReviewResponse> getByCourse(String courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return reviewRepository.findByCourse(course)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReviewResponse mapToResponse(Review review) {

        Course course = review.getCourse();

        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())

                // Student
                .studentId(review.getStudent().getId())
                .studentName(
                        review.getStudent().getFirstName() + " " +
                        review.getStudent().getLastName()
                )
                .studentEmail(review.getStudent().getEmail())
                .studentProfileImage(review.getStudent().getProfileImage())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseThumbnail(course.getThumbnail())

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

    /**
     * Recalculate and update the average rating of a course.
     */
    private void updateCourseRating(Course course) {

        List<Review> reviews = reviewRepository.findByCourse(course);

        if (reviews.isEmpty()) {
            course.setRating(0.0);
        } else {

            double average = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            // Round to 1 decimal place
            average = Math.round(average * 10.0) / 10.0;

            course.setRating(average);
        }

        courseRepository.save(course);
    }

}