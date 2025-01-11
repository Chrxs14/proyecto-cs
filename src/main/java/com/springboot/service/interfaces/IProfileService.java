package com.springboot.service.interfaces;



import com.springboot.dto.ProfileDTO;

import java.util.List;
import java.util.UUID;

public interface IProfileService {
    ProfileDTO getProfileById(UUID id);
    
    List<ProfileDTO> getProfiles();
    
    ProfileDTO getProfileByUserId(UUID userId);
    
    ProfileDTO createProfile(ProfileDTO profileDTO);
    
    ProfileDTO updateProfile(UUID id, ProfileDTO profileDTO);
    
    void deleteProfileById(UUID id);
}