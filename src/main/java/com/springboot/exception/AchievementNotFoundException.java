package com.springboot.exception;

public class AchievementNotFoundException extends RuntimeException {
    public AchievementNotFoundException(Long id) {
        super("Achievement not found with ID: " + id);
    }   
}