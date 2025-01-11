package com.springboot.repository.user;


import com.springboot.entity.user.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long>{
    @Query("SELECT a FROM Achievement a WHERE a.user.id = :userId")
    List<Achievement> findByUserId(UUID userId);
}