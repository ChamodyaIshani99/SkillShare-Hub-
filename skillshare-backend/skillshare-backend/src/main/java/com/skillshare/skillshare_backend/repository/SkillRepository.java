package com.skillshare.skillshare_backend.repository;

import com.skillshare.skillshare_backend.entity.Skill;
import com.skillshare.skillshare_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<Skill, String> {

    List<Skill> findByCategory_Id(String categoryId);

    List<Skill> findByInstructor(User instructor);

    List<Skill> findByActiveTrue();

    boolean existsByTitle(String title);

}