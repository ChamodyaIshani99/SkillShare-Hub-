package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.LessonRequest;
import com.skillshare.skillshare_backend.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {

    // Instructor
    LessonResponse create(LessonRequest request);

    LessonResponse update(String id, LessonRequest request);

    void delete(String id);

    // Student
    LessonResponse getById(String id);

    List<LessonResponse> getByCourse(String courseId);

    List<LessonResponse> getPreviewLessons(String courseId);

    // General
    List<LessonResponse> getAll();

}