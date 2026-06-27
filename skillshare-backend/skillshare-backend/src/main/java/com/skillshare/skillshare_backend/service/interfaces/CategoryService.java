package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.CategoryRequest;
import com.skillshare.skillshare_backend.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse getById(String id);

    CategoryResponse update(String id, CategoryRequest request);

    void delete(String id);

}