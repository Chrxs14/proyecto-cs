package com.springboot.service.interfaces;

import com.springboot.dto.SkillDTO;

import java.util.List;
import java.util.UUID;

public interface ISkillService {
    List<SkillDTO> getAllSkills();
    
    SkillDTO getSkillById(Long id);
    
    List<SkillDTO> getSkillsByUserId(UUID userId);
}