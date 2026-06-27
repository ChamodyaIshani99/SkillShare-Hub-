package com.skillshare.skillshare_backend.service.impl;

import com.skillshare.skillshare_backend.dto.request.SkillRequest;
import com.skillshare.skillshare_backend.dto.response.SkillResponse;
import com.skillshare.skillshare_backend.entity.Category;
import com.skillshare.skillshare_backend.entity.Skill;
import com.skillshare.skillshare_backend.entity.User;
import com.skillshare.skillshare_backend.repository.CategoryRepository;
import com.skillshare.skillshare_backend.repository.SkillRepository;
import com.skillshare.skillshare_backend.repository.UserRepository;
import com.skillshare.skillshare_backend.service.interfaces.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public SkillResponse create(SkillRequest request) {

        if (skillRepository.existsByTitle(request.getTitle())) {
            throw new RuntimeException("Skill already exists.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User instructor = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Skill skill = Skill.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .level(request.getLevel())
                .image(request.getImage())
                .category(category)
                .instructor(instructor)
                .active(true)
                .build();

        return mapToResponse(skillRepository.save(skill));
    }

    @Override
    public List<SkillResponse> getAll() {

        return skillRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SkillResponse getById(String id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        return mapToResponse(skill);
    }

    @Override
    public SkillResponse update(String id, SkillRequest request) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!skill.getInstructor().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("You cannot update this skill.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        skill.setTitle(request.getTitle());
        skill.setDescription(request.getDescription());
        skill.setLevel(request.getLevel());
        skill.setImage(request.getImage());
        skill.setCategory(category);

        return mapToResponse(skillRepository.save(skill));
    }

    @Override
    public void delete(String id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!skill.getInstructor().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("You cannot delete this skill.");
        }

        skillRepository.delete(skill);
    }

    @Override
    public List<SkillResponse> getByCategory(String categoryId) {

        return skillRepository.findByCategory_Id(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<SkillResponse> getMySkills() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User instructor = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        return skillRepository.findByInstructor(instructor)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SkillResponse mapToResponse(Skill skill) {

        return SkillResponse.builder()
                .id(skill.getId())
                .title(skill.getTitle())
                .description(skill.getDescription())
                .level(skill.getLevel())
                .image(skill.getImage())
                .categoryId(skill.getCategory().getId())
                .categoryName(skill.getCategory().getName())
                .instructorId(skill.getInstructor().getId())
                .instructorName(
                        skill.getInstructor().getFirstName() + " " +
                        skill.getInstructor().getLastName()
                )
                .build();
    }
}