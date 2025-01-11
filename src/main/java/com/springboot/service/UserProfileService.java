package com.springboot.service;

import com.springboot.dto.PostDTO;
import com.springboot.dto.ProfileDTO;
import com.springboot.dto.UserProfileDTO;
import com.springboot.entity.user.Profile;
import com.springboot.entity.user.User;
import com.springboot.repository.user.ProfileRepository;
import com.springboot.repository.user.UserRepository;
import com.springboot.service.interfaces.IUserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService implements IUserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PostService postService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private AchievementService achievementService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public UserProfileDTO getAuthenticatedUserProfile() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No user is authenticated");
        }

        String email = authentication.getName();

        // Encontrar el usuario por email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Obtener el perfil del usuario
        ProfileDTO profile = profileService.getProfileByUserId(user.getId());
        List<PostDTO> posts = postService.getPostsByUserId(user.getId());

        return UserProfileDTO.builder()
                .profile(profile)
                .posts(posts)
                .skills(new ArrayList<>()) // Agrega lógica para habilidades
                .achivements(new ArrayList<>()) // Agrega lógica para logros
                .events(new ArrayList<>()) // Agrega lógica para eventos
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public UserProfileDTO getUserProfileById(UUID id) {
        logger.info("Fetching User Profile with ID: {}", id);
        ProfileDTO profileDTO = profileService.getProfileById(id);
        return mapToDTO(profileDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileDTO getUserProfileByUserId(UUID userId) {
        logger.info("Fetching User Profile data for user with ID: {}", userId);
        ProfileDTO profileDTO = profileService.getProfileByUserId(userId);
        return mapToDTO(profileDTO);
    }

    @Transactional
    @Override
    public UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO) {
        logger.info("Creating a new User Profile...");

        Profile profileEntity = mapToEntity(userProfileDTO);

        Profile savedProfile = profileService.mapToEntity(profileService.createProfile(profileService.mapToDTO(profileEntity)));

        logger.info("Profile created successfully with ID: {}", savedProfile.getId());

        return getUserProfileByUserId(savedProfile.getUser().getId());
    }

    @Transactional
    @Override
    public UserProfileDTO updateUserProfile(UUID id, UserProfileDTO updatedProfileDTO) {
        logger.info("Updating User Profile with ID: {}", id);

        Profile existingProfile = profileService.mapToEntity(profileService.getProfileById(id));

        existingProfile.setBio(updatedProfileDTO.getProfile().getBio());

        Profile updatedProfile = profileService.mapToEntity(profileService.updateProfile(id, profileService.mapToDTO(existingProfile)));

        logger.info("Profile updated successfully for ID: {}", updatedProfile.getId());

        return getUserProfileById(updatedProfile.getId());
    }

    @Override
    public void deleteUserProfileById(UUID id) {
        profileService.deleteProfileById(id);
    }

    @Override
    public void deleteUserProfileByUserId(UUID userId) {
        ProfileDTO profileDTO = profileService.getProfileByUserId(userId);
        profileService.deleteProfileById(profileDTO.getId());
    }

    private UserProfileDTO mapToDTO(ProfileDTO profileDTO) {
        return UserProfileDTO.builder()
                .profile(profileDTO)
                .posts(postService.getPostsByUserId(profileDTO.getUser().getId()))
                .skills(skillService.getSkillsByUserId(profileDTO.getUser().getId()))
                .achivements(achievementService.getAchievementsByUserId(profileDTO.getUser().getId()))
                .build();
    }

    private Profile mapToEntity(UserProfileDTO userProfileDTO) {
        ProfileDTO profileDTO = userProfileDTO.getProfile();

        return Profile.builder()
                .id(profileDTO.getId())
                .bio(profileDTO.getBio())
                .user(userService.mapToEntity(userService.getUserById(profileDTO.getUser().getId())))
                .build();
    }
}