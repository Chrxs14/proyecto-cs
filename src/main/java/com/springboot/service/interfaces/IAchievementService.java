package com.springboot.service.interfaces;


import com.springboot.dto.AchievementDTO;

import java.util.List;
import java.util.UUID;

public interface IAchievementService {
    List<AchievementDTO> getAllAchievements();
    
    AchievementDTO getAchievementById(Long id);
    
    List<AchievementDTO> getAchievementsByUserId(UUID userId);
    
    AchievementDTO createAchievement(AchievementDTO achievementDTO);
}