package com.springboot.repository.user;

import com.springboot.entity.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT us.skill FROM UserSkill us WHERE us.user.id = :userId")
    List<Skill> findSkillsByUserId(UUID userId);
}