package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.CourseRequest;
import com.skillshare.skillshare_backend.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {

    // Instructor
    CourseResponse create(CourseRequest request);

    CourseResponse update(String id, CourseRequest request);

    void delete(String id);

    CourseResponse publish(String id);

    CourseResponse unpublish(String id);

    List<CourseResponse> getMyCourses();

    // Student
    List<CourseResponse> getAllPublishedCourses();

    CourseResponse getById(String id);

    List<CourseResponse> getBySkill(String skillId);

    List<CourseResponse> search(String keyword);

    // Admin / General
    List<CourseResponse> getAll();

}
