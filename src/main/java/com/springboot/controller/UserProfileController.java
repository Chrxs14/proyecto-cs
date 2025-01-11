package com.springboot.controller;

import com.springboot.dto.UserProfileDTO;
import com.springboot.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user-profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // Mostrar un formulario para consultar un perfil por ID
    @GetMapping
    public String showUserProfileForm() {
        return "user-profile-form";
    }

    // Mostrar el perfil del usuario autenticado
    @GetMapping("/me")
    public String getAuthenticatedUserProfile(Model model) {
        UserProfileDTO userProfileDTO = userProfileService.getAuthenticatedUserProfile();
        model.addAttribute("userProfile", userProfileDTO);
        return "user-profile-detail";
    }

    // Consultar un perfil de usuario por ID
    @GetMapping("/{id}")
    public String getUserProfileById(@PathVariable UUID id, Model model) {
        UserProfileDTO userProfileDTO = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfileDTO);
        return "user-profile-detail";
    }

    // Mostrar formulario para crear un nuevo perfil
    @GetMapping("/create")
    public String showCreateUserProfileForm(Model model) {
        model.addAttribute("userProfile", new UserProfileDTO());
        return "create-user-profile";
    }

    // Crear un nuevo perfil
    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute UserProfileDTO userProfileDTO, Model model) {
        UserProfileDTO createdProfile = userProfileService.createUserProfile(userProfileDTO);
        model.addAttribute("userProfile", createdProfile);
        return "user-profile-detail";
    }

    // Mostrar formulario para actualizar un perfil
    @GetMapping("/update/{id}")
    public String showUpdateUserProfileForm(@PathVariable UUID id, Model model) {
        UserProfileDTO userProfileDTO = userProfileService.getUserProfileById(id);
        model.addAttribute("userProfile", userProfileDTO);
        return "update-user-profile";
    }

    // Actualizar un perfil
    @PostMapping("/update/{id}")
    public String updateUserProfile(@PathVariable UUID id, @ModelAttribute UserProfileDTO userProfileDTO, Model model) {
        UserProfileDTO updatedProfile = userProfileService.updateUserProfile(id, userProfileDTO);
        model.addAttribute("userProfile", updatedProfile);
        return "user-profile-detail";
    }
}