package com.springboot.service;

import com.springboot.dto.AchievementDTO;
import com.springboot.entity.user.Achievement;
import com.springboot.exception.AchievementNotFoundException;
import com.springboot.repository.user.AchievementRepository;
import com.springboot.service.interfaces.IAchievementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AchievementService implements IAchievementService {
    
    private static final Logger logger = LoggerFactory.getLogger(AchievementService.class);
    
    @Autowired
    private AchievementRepository achievementRepository;
    
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public List<AchievementDTO> getAllAchievements() {
        logger.info("Fetching all achievement...");

        List<Achievement> achievement = achievementRepository.findAll();
        logger.info("{} achievement found.", achievement.size());

        return achievement.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    @Override
    public AchievementDTO getAchievementById(Long id) {
        logger.info("Fetching achievement with ID: {}", id);

        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(id));

        return mapToDTO(achievement); 
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<AchievementDTO> getAchievementsByUserId(UUID userId) {
        logger.info("Fetching achievement for user with ID: {}", userId);

        List<Achievement> achievement = achievementRepository.findByUserId(userId);

        logger.info("{} achievement found for user with ID: {}", achievement.size(), userId);

        return achievement.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    @Override
    public AchievementDTO createAchievement(AchievementDTO achievementDTO) {
        logger.info("Creating a new achievement...");

        Achievement achievement = mapToEntity(achievementDTO);

        Achievement savedAchievement = achievementRepository.save(achievement);
        logger.info("Achievement created successfully with ID: {}", savedAchievement.getId());

        return mapToDTO(savedAchievement);
    }
    
    public AchievementDTO mapToDTO(Achievement achievement) {
        logger.debug("Mapping Achievement entity to AchievementDTO...");
        return AchievementDTO.builder()
                .id(achievement.getId())
                .user(userService.getUserById(achievement.getUser().getId()))
                .description(achievement.getDescription())
                .achived_date(achievement.getAchived_date())
                .build();
    }
    
    public Achievement mapToEntity(AchievementDTO achievementDTO) {
        logger.debug("Mapping AchievementDTO to Achievement entity...");
        return Achievement.builder()
                .id(achievementDTO.getId())
                .user(userService.mapToEntity(userService.getUserById(achievementDTO.getUser().getId())))
                .description(achievementDTO.getDescription())
                .achived_date(achievementDTO.getAchived_date())
                .build();
    }
}