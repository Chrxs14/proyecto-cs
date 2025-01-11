package com.springboot.config;

import com.springboot.entity.user.Role;
import com.springboot.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("User").isEmpty()) {
            Role userRole = new Role(null, "User");
            roleRepository.save(userRole);
        }
        if (roleRepository.findByName("Admin").isEmpty()) {
            Role adminRole = new Role(null, "Admin");
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName("Teacher").isEmpty()) {
            Role adminRole = new Role(null, "Teacher");
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName("Student").isEmpty()) {
            Role adminRole = new Role(null, "Student");
            roleRepository.save(adminRole);
        }
    }
}
