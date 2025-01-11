package com.springboot.service;

import com.springboot.dto.SkillDTO;
import com.springboot.entity.user.Skill;
import com.springboot.exception.SkillNotFoundException;
import com.springboot.repository.user.SkillRepository;
import com.springboot.service.interfaces.ISkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class SkillService implements ISkillService {
    
    private static final Logger logger = LoggerFactory.getLogger(SkillService.class);
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Transactional(readOnly = true)
    @Override
    public List<SkillDTO> getAllSkills() {
       logger.info("Fetching all Skills...");

        List<Skill> skills = skillRepository.findAll();
        logger.info("{} skills found.", skills.size());

        return skills.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public SkillDTO getSkillById(Long id) {
        logger.info("Fetching skill with ID: {}", id);

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));

        return mapToDTO(skill);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<SkillDTO> getSkillsByUserId(UUID userId) {
        logger.info("Fetching all Skills...");

        List<Skill> skills = skillRepository.findSkillsByUserId(userId);
        logger.info("{} skills found for user with ID: {}.", skills.size(), userId);

        return skills.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public Skill mapToEntity(SkillDTO skillDTO) {
       logger.debug("Mapping SkillDTO to Skill entity...");
       return Skill.builder()
               .id(skillDTO.getId())
               .name(skillDTO.getName())
               .build();
    }
    
    public SkillDTO mapToDTO(Skill skill) {
        logger.debug("Mapping Skill entity to SkillDTO...");
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}