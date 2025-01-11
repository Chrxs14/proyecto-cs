package com.springboot.service;

import com.springboot.dto.ProfileDTO;
import com.springboot.entity.user.Profile;
import com.springboot.exception.ProfileNotFoundException;
import com.springboot.repository.user.ProfileRepository;
import com.springboot.service.interfaces.IProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileService implements IProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private UserService userService;
    
    @Transactional(readOnly = true)
    @Override
    public ProfileDTO getProfileById(UUID id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        
        return mapToDTO(profile);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfileDTO> getProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        
        return profiles.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileDTO getProfileByUserId(UUID userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId, true));
        
        return mapToDTO(profile);
    }
    
    @Transactional
    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = mapToEntity(profileDTO);
        
        Profile savedProfile = profileRepository.save(profile);
        
        return mapToDTO(savedProfile);
    }

    @Transactional
    @Override
    public ProfileDTO updateProfile(UUID id, ProfileDTO profileDTO) {
        Profile existingProfile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        
        existingProfile.setBio(profileDTO.getBio());
        
        Profile updatedProfile = profileRepository.save(existingProfile);
        return mapToDTO(updatedProfile);
    }
    
    @Transactional
    @Override
    public void deleteProfileById(UUID id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        
        profileRepository.delete(profile);
    }
    
    public ProfileDTO mapToDTO(Profile profile) {
        return ProfileDTO.builder()
                .id(profile.getId())
                .user(userService.getUserById(profile.getUser().getId()))
                .bio(profile.getBio())
                .build();
    }
    
    public Profile mapToEntity(ProfileDTO profileDTO) {
        return Profile.builder()
                .id(profileDTO.getId())
                .user(userService.mapToEntity(userService.getUserById(profileDTO.getUser().getId())))
                .bio(profileDTO.getBio())
                .build();
    }
}