package com.springboot.exception;

public class SkillNotFoundException extends RuntimeException {    
    public SkillNotFoundException(Long id) {
        super("Skill not found with ID: " + id);
    }
}