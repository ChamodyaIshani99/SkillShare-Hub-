package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.SkillRequest;
import com.skillshare.skillshare_backend.dto.response.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse create(SkillRequest request);

    List<SkillResponse> getAll();

    SkillResponse getById(String id);

    SkillResponse update(String id, SkillRequest request);

    void delete(String id);

    List<SkillResponse> getByCategory(String categoryId);

    List<SkillResponse> getMySkills();

}