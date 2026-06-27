package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.response.CourseAnalyticsResponse;
import com.skillshare.skillshare_backend.dto.response.DashboardResponse;
import com.skillshare.skillshare_backend.dto.response.RevenueAnalyticsResponse;
import com.skillshare.skillshare_backend.dto.response.UserAnalyticsResponse;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.enums.PaymentStatus;
import com.skillshare.skillshare_backend.enums.Role;
import com.skillshare.skillshare_backend.repository.*;
import com.skillshare.skillshare_backend.service.interfaces.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final CertificateRepository certificateRepository;
    private final WishlistRepository wishlistRepository;

    @Override
    public DashboardResponse getAdminDashboard() {

        long totalUsers = userRepository.count();

        long totalStudents =
                userRepository.findByRole(Role.STUDENT).size();

        long totalInstructors =
                userRepository.findByRole(Role.INSTRUCTOR).size();

        long totalCourses =
                courseRepository.count();

        long totalLessons =
                lessonRepository.count();

        long totalEnrollments =
                enrollmentRepository.count();

        long totalPayments =
                paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS
                ).size();

        double totalRevenue =
                paymentRepository.findByPaymentStatus(
                                PaymentStatus.SUCCESS
                        )
                        .stream()
                        .mapToDouble(payment -> payment.getAmount())
                        .sum();

        long totalReviews =
                reviewRepository.count();

        double averageRating =
                reviewRepository.findAll()
                        .stream()
                        .mapToDouble(review -> review.getRating())
                        .average()
                        .orElse(0.0);

        long totalCertificates =
                certificateRepository.count();

        long totalWishlist =
                wishlistRepository.count();

        return DashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalStudents(totalStudents)
                .totalInstructors(totalInstructors)
                .totalCourses(totalCourses)
                .totalLessons(totalLessons)
                .totalEnrollments(totalEnrollments)
                .totalPayments(totalPayments)
                .totalRevenue(totalRevenue)
                .totalReviews(totalReviews)
                .averageRating(averageRating)
                .totalCertificates(totalCertificates)
                .totalWishlist(totalWishlist)
                .build();
    }
        @Override
    public DashboardResponse getInstructorDashboard() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User instructor = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        long totalCourses = courseRepository.findByInstructor(instructor).size();

        long totalStudents = enrollmentRepository.findAll()
                .stream()
                .filter(enrollment ->
                        enrollment.getCourse()
                                .getInstructor()
                                .getId()
                                .equals(instructor.getId()))
                .count();

        double totalRevenue = paymentRepository.findAll()
                .stream()
                .filter(payment ->
                        payment.getPaymentStatus() == PaymentStatus.SUCCESS)
                .filter(payment ->
                        payment.getCourse()
                                .getInstructor()
                                .getId()
                                .equals(instructor.getId()))
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        long totalReviews = reviewRepository.findAll()
                .stream()
                .filter(review ->
                        review.getCourse()
                                .getInstructor()
                                .getId()
                                .equals(instructor.getId()))
                .count();

        double averageRating = reviewRepository.findAll()
                .stream()
                .filter(review ->
                        review.getCourse()
                                .getInstructor()
                                .getId()
                                .equals(instructor.getId()))
                .mapToDouble(review -> review.getRating())
                .average()
                .orElse(0.0);

        return DashboardResponse.builder()
                .totalCourses(totalCourses)
                .totalEnrollments(totalStudents)
                .totalRevenue(totalRevenue)
                .totalReviews(totalReviews)
                .averageRating(averageRating)
                .build();
    }

    @Override
    public DashboardResponse getStudentDashboard() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        long totalEnrollments =
                enrollmentRepository.findByStudent(student).size();

        long completedCourses =
                enrollmentRepository.findByStudent(student)
                        .stream()
                        .filter(enrollment ->
                                Boolean.TRUE.equals(enrollment.getCompleted()))
                        .count();

        long totalCertificates =
                certificateRepository.findByStudent(student).size();

        long totalWishlist =
                wishlistRepository.findByStudent(student).size();

        long totalPayments =
                paymentRepository.findByStudent(student)
                        .stream()
                        .filter(payment ->
                                payment.getPaymentStatus() == PaymentStatus.SUCCESS)
                        .count();

        double averageProgress =
                enrollmentRepository.findByStudent(student)
                        .stream()
                        .mapToInt(enrollment -> enrollment.getProgress())
                        .average()
                        .orElse(0.0);

        return DashboardResponse.builder()
                .totalEnrollments(totalEnrollments)
                .totalCourses(completedCourses)
                .totalCertificates(totalCertificates)
                .totalWishlist(totalWishlist)
                .totalPayments(totalPayments)
                .averageRating(averageProgress)
                .build();
    }
        @Override
    public RevenueAnalyticsResponse getRevenueAnalytics() {

        double totalRevenue = paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS)
                .stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        long totalPayments = paymentRepository.findByPaymentStatus(
                PaymentStatus.SUCCESS).size();

        double refundedAmount = paymentRepository.findByPaymentStatus(
                        PaymentStatus.REFUNDED)
                .stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        long refundedPayments = paymentRepository.findByPaymentStatus(
                PaymentStatus.REFUNDED).size();

        // Simplified implementation.
        // You can later calculate actual daily, weekly, monthly, yearly values
        // using payment.getPaymentDate().

        return RevenueAnalyticsResponse.builder()
                .todayRevenue(totalRevenue)
                .weeklyRevenue(totalRevenue)
                .monthlyRevenue(totalRevenue)
                .yearlyRevenue(totalRevenue)
                .totalRevenue(totalRevenue)

                .todayPayments(totalPayments)
                .weeklyPayments(totalPayments)
                .monthlyPayments(totalPayments)
                .yearlyPayments(totalPayments)
                .totalPayments(totalPayments)

                .refundedAmount(refundedAmount)
                .refundedPayments(refundedPayments)
                .build();
    }

    @Override
    public CourseAnalyticsResponse getCourseAnalytics() {

        long totalCourses = courseRepository.count();

        long activeCourses = courseRepository.findAll()
                .stream()
                .filter(Course::getActive)
                .count();

        long inactiveCourses = totalCourses - activeCourses;

        long totalEnrollments = enrollmentRepository.count();

        long completedEnrollments = enrollmentRepository.findAll()
                .stream()
                .filter(enrollment ->
                        Boolean.TRUE.equals(enrollment.getCompleted()))
                .count();

        long activeEnrollments =
                totalEnrollments - completedEnrollments;

        double averageRating = reviewRepository.findAll()
                .stream()
                .mapToDouble(review -> review.getRating())
                .average()
                .orElse(0.0);

        double highestRating = reviewRepository.findAll()
                .stream()
                .mapToDouble(review -> review.getRating())
                .max()
                .orElse(0.0);

        String topRatedCourse = reviewRepository.findAll()
                .stream()
                .max((r1, r2) ->
                        Double.compare(r1.getRating(), r2.getRating()))
                .map(review -> review.getCourse().getTitle())
                .orElse("N/A");

        String mostEnrolledCourse = enrollmentRepository.findAll()
                .stream()
                .max((e1, e2) ->
                        Integer.compare(
                                e1.getCourse().getTotalStudents(),
                                e2.getCourse().getTotalStudents()))
                .map(enrollment -> enrollment.getCourse().getTitle())
                .orElse("N/A");

        String mostWishlistedCourse = wishlistRepository.findAll()
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        wishlist -> wishlist.getCourse().getTitle(),
                        java.util.stream.Collectors.counting()))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("N/A");

        String highestRevenueCourse = paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS)
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        payment -> payment.getCourse().getTitle(),
                        java.util.stream.Collectors.summingDouble(
                                payment -> payment.getAmount())))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("N/A");

        double totalRevenue = paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS)
                .stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        double averageCourseRevenue =
                totalCourses == 0 ? 0.0 : totalRevenue / totalCourses;

        return CourseAnalyticsResponse.builder()
                .totalCourses(totalCourses)
                .activeCourses(activeCourses)
                .inactiveCourses(inactiveCourses)

                .totalEnrollments(totalEnrollments)
                .completedEnrollments(completedEnrollments)
                .activeEnrollments(activeEnrollments)

                .averageCourseRating(averageRating)
                .highestCourseRating(highestRating)

                .topRatedCourse(topRatedCourse)
                .mostEnrolledCourse(mostEnrolledCourse)
                .mostWishlistedCourse(mostWishlistedCourse)
                .highestRevenueCourse(highestRevenueCourse)

                .totalRevenue(totalRevenue)
                .averageCourseRevenue(averageCourseRevenue)
                .build();
    }
        @Override
    public UserAnalyticsResponse getUserAnalytics() {

        long totalUsers = userRepository.count();

        long totalStudents =
                userRepository.findByRole(Role.STUDENT).size();

        long totalInstructors =
                userRepository.findByRole(Role.INSTRUCTOR).size();

        long totalAdmins =
                userRepository.findByRole(Role.ADMIN).size();

        long activeUsers = userRepository.findAll()
                .stream()
                .filter(User::getEnabled)
                .count();

        long inactiveUsers = totalUsers - activeUsers;

        long verifiedUsers = userRepository.findAll()
                .stream()
                .filter(User::getEmailVerified)
                .count();

        long unverifiedUsers = totalUsers - verifiedUsers;

        // Simplified registration statistics.
        // Later you can calculate actual daily/weekly/monthly/yearly
        // values using the user's createdAt field.

        long todayRegistrations = totalUsers;
        long weeklyRegistrations = totalUsers;
        long monthlyRegistrations = totalUsers;
        long yearlyRegistrations = totalUsers;

        String mostActiveStudent = enrollmentRepository.findAll()
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        enrollment -> enrollment.getStudent(),
                        java.util.stream.Collectors.counting()))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(entry ->
                        entry.getKey().getFirstName() + " " +
                        entry.getKey().getLastName())
                .orElse("N/A");

        String mostActiveInstructor = courseRepository.findAll()
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        course -> course.getInstructor(),
                        java.util.stream.Collectors.counting()))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(entry ->
                        entry.getKey().getFirstName() + " " +
                        entry.getKey().getLastName())
                .orElse("N/A");

        double ratio = totalInstructors == 0
                ? 0.0
                : (double) totalStudents / totalInstructors;

        return UserAnalyticsResponse.builder()
                .totalUsers(totalUsers)
                .totalStudents(totalStudents)
                .totalInstructors(totalInstructors)
                .totalAdmins(totalAdmins)

                .activeUsers(activeUsers)
                .inactiveUsers(inactiveUsers)

                .verifiedUsers(verifiedUsers)
                .unverifiedUsers(unverifiedUsers)

                .todayRegistrations(todayRegistrations)
                .weeklyRegistrations(weeklyRegistrations)
                .monthlyRegistrations(monthlyRegistrations)
                .yearlyRegistrations(yearlyRegistrations)

                .mostActiveStudent(mostActiveStudent)
                .mostActiveInstructor(mostActiveInstructor)

                .studentInstructorRatio(ratio)

                .build();
    }

}