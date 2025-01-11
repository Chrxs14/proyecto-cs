package com.springboot.controller;

import com.springboot.dto.UserDTO;
import com.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegisterUserController {
    private UserService usuarioServicio;

    public RegisterUserController(UserService usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    @ModelAttribute("user")
    public UserDTO retornarNuevoUsuarioRegistroDTO() {
        return new UserDTO();
    }

    @GetMapping
    public String mostrarFormularioDeRegistro() {
        return "registro";
    }

    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute("user") UserDTO registroDTO) {
        usuarioServicio.createUser(registroDTO);
        return "redirect:/registro?exito";
    }
}
