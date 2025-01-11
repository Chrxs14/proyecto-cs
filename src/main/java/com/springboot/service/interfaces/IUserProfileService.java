package com.springboot.service.interfaces;


import com.springboot.dto.UserProfileDTO;

import java.util.UUID;

public interface IUserProfileService {
    UserProfileDTO getUserProfileById(UUID id);
    
    UserProfileDTO getUserProfileByUserId(UUID userId);

    UserProfileDTO createUserProfile(UserProfileDTO profile);

    UserProfileDTO updateUserProfile(UUID id, UserProfileDTO updatedProfile);

    void deleteUserProfileById(UUID id);
    
    void deleteUserProfileByUserId(UUID userId);
}