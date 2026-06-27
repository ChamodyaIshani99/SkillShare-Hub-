package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.WishlistRequest;
import com.skillshare.skillshare_backend.dto.response.WishlistResponse;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.entity.Wishlist;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.repository.WishlistRepository;
import com.skillshare.skillshare_backend.service.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public WishlistResponse addToWishlist(WishlistRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (wishlistRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Course already exists in your wishlist.");
        }

        Wishlist wishlist = Wishlist.builder()
                .student(student)
                .course(course)
                .build();

        wishlist = wishlistRepository.save(wishlist);

        return mapToResponse(wishlist);
    }

    @Override
    public void removeFromWishlist(String wishlistId) {

        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!wishlist.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You cannot remove this wishlist item.");
        }

        wishlistRepository.delete(wishlist);
    }
        @Override
    public List<WishlistResponse> getMyWishlist() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return wishlistRepository.findByStudent(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public boolean isInWishlist(String courseId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return wishlistRepository.existsByStudentAndCourse(student, course);
    }

    @Override
    public WishlistResponse getById(String wishlistId) {

        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!wishlist.getStudent()
                .getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException("You are not authorized to view this wishlist item.");
        }

        return mapToResponse(wishlist);
    }
        @Override
    public List<WishlistResponse> getAllWishlist() {

        return wishlistRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private WishlistResponse mapToResponse(Wishlist wishlist) {

        Course course = wishlist.getCourse();

        return WishlistResponse.builder()
                .id(wishlist.getId())
                .createdAt(wishlist.getCreatedAt())

                // Student
                .studentId(wishlist.getStudent().getId())
                .studentName(
                        wishlist.getStudent().getFirstName() + " " +
                        wishlist.getStudent().getLastName()
                )
                .studentEmail(wishlist.getStudent().getEmail())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseDescription(course.getDescription())
                .courseThumbnail(course.getThumbnail())
                .coursePrice(course.getPrice())
                .courseDuration(course.getDuration())
                .courseLevel(course.getLevel())
                .courseLanguage(course.getLanguage())
                .courseRating(course.getRating())
                .totalStudents(course.getTotalStudents())

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