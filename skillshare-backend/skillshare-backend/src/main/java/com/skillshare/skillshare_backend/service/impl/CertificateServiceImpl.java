package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.CertificateRequest;
import com.skillshare.skillshare_backend.dto.response.CertificateResponse;
import com.skillshare.skillshare_backend.entity.Certificate;
import com.skillshare.skillshare_backend.entity.Course;
import com.skillshare.skillshare_backend.entity.Enrollment;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.repository.CertificateRepository;
import com.skillshare.skillshare_backend.repository.CourseRepository;
import com.skillshare.skillshare_backend.repository.EnrollmentRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public CertificateResponse generate(CertificateRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = enrollmentRepository
                .findByStudentAndCourse(student, course)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (!Boolean.TRUE.equals(enrollment.getCompleted())) {
            throw new RuntimeException("Complete the course before generating a certificate.");
        }

        if (certificateRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Certificate already generated.");
        }

        Certificate certificate = Certificate.builder()
                .certificateNumber(generateCertificateNumber())
                .student(student)
                .course(course)
                .completionDate(LocalDateTime.now())
                .issueDate(LocalDateTime.now())
                .verified(true)

                // Later this will store the generated PDF URL
                .certificateUrl(null)

                .build();

        certificate = certificateRepository.save(certificate);

        return mapToResponse(certificate);
    }
        @Override
    public List<CertificateResponse> getMyCertificates() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return certificateRepository.findByStudent(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CertificateResponse getById(String certificateId) {

        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUser = authentication.getName();

        boolean isStudent = certificate.getStudent()
                .getEmail()
                .equals(currentUser);

        boolean isInstructor = certificate.getCourse()
                .getInstructor()
                .getEmail()
                .equals(currentUser);

        if (!isStudent && !isInstructor) {
            throw new RuntimeException("You are not authorized to view this certificate.");
        }

        return mapToResponse(certificate);
    }

    @Override
    public CertificateResponse verifyCertificate(String certificateNumber) {

        Certificate certificate = certificateRepository
                .findByCertificateNumber(certificateNumber)
                .orElseThrow(() ->
                        new RuntimeException("Certificate not found"));

        if (!Boolean.TRUE.equals(certificate.getVerified())) {
            throw new RuntimeException("Certificate is not valid.");
        }

        return mapToResponse(certificate);
    }
        @Override
    public List<CertificateResponse> getAllCertificates() {

        return certificateRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CertificateResponse mapToResponse(Certificate certificate) {

        Course course = certificate.getCourse();

        return CertificateResponse.builder()
                .id(certificate.getId())
                .certificateNumber(certificate.getCertificateNumber())
                .issueDate(certificate.getIssueDate())
                .completionDate(certificate.getCompletionDate())
                .certificateUrl(certificate.getCertificateUrl())
                .verified(certificate.getVerified())

                // Student
                .studentId(certificate.getStudent().getId())
                .studentName(
                        certificate.getStudent().getFirstName() + " " +
                        certificate.getStudent().getLastName()
                )
                .studentEmail(certificate.getStudent().getEmail())

                // Course
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseThumbnail(course.getThumbnail())
                .courseRating(course.getRating())

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

    /**
     * Generate a unique certificate number.
     * Example: CERT-2026-3F8A7C2D
     */
    private String generateCertificateNumber() {

        String certificateNumber;

        do {
            certificateNumber = "CERT-" +
                    Year.now().getValue() +
                    "-" +
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();

        } while (certificateRepository.existsByCertificateNumber(certificateNumber));

        return certificateNumber;
    }

}