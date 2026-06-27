package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.request.SkillRequest;
import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.SkillResponse;
import com.skillshare.skillshare_backend.service.interfaces.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponse>> createSkill(
            @Valid @RequestBody SkillRequest request) {

        SkillResponse response = skillService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<SkillResponse>builder()
                        .success(true)
                        .message("Skill created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkills() {

        List<SkillResponse> response = skillService.getAll();

        return ResponseEntity.ok(
                ApiResponse.<List<SkillResponse>>builder()
                        .success(true)
                        .message("Skills retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> getSkillById(
            @PathVariable String id) {

        SkillResponse response = skillService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<SkillResponse>builder()
                        .success(true)
                        .message("Skill retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> updateSkill(
            @PathVariable String id,
            @Valid @RequestBody SkillRequest request) {

        SkillResponse response = skillService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.<SkillResponse>builder()
                        .success(true)
                        .message("Skill updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSkill(
            @PathVariable String id) {

        skillService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Skill deleted successfully")
                        .data("Skill deleted successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/category/{categoryId}")
public ResponseEntity<ApiResponse<List<SkillResponse>>> getSkillsByCategory(
        @PathVariable String categoryId) {

    return ResponseEntity.ok(
            ApiResponse.<List<SkillResponse>>builder()
                    .success(true)
                    .message("Skills retrieved successfully")
                    .data(skillService.getByCategory(categoryId))
                    .timestamp(LocalDateTime.now())
                    .build()
    );
}

    @GetMapping("/my-skills")
public ResponseEntity<ApiResponse<List<SkillResponse>>> getMySkills() {

    return ResponseEntity.ok(
            ApiResponse.<List<SkillResponse>>builder()
                    .success(true)
                    .message("My skills retrieved successfully")
                    .data(skillService.getMySkills())
                    .timestamp(LocalDateTime.now())
                    .build()
    );
}
}