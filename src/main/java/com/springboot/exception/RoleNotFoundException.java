package com.springboot.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("Role not found with NAME: " + name);
    }
    
    public RoleNotFoundException(Long id) {
        super("Role not found with ID: " + id);
    }
}