package com.springboot.controller;

import com.springboot.dto.UserDTO;
import com.springboot.exception.UserNotFoundException;
import com.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable UUID id, Model model) {
        try {
            UserDTO user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "user-details"; 
        } catch (UserNotFoundException e) {
            return "user-not-found";
        }
    }

    @GetMapping("/form")
    public String showUserForm(@RequestParam(value = "id", required = false) UUID id, Model model) {
        if (id != null) {
            try {
                UserDTO user = userService.getUserById(id);
                model.addAttribute("user", user);
                return "users-form";
            } catch (UserNotFoundException e) {
                return "user-not-found";
            }
        }
        model.addAttribute("user", new UserDTO());
        return "users-form";
    }

    @PostMapping
    public String saveOrUpdateUser(@Valid @ModelAttribute UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "users-form";
        }

        if (user.getId() != null) {
            userService.updateUser(user.getId(), user);
        } else {
            userService.createUser(user);
        }

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}