package com.springboot.exception;

import java.util.UUID;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(UUID id) {
        super("Profile not found with ID: " + id);
    }
    public ProfileNotFoundException(UUID userId, boolean isUser) {
        super("Profile not found for user ID: " + userId);
    }
}
