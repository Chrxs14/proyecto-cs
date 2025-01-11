package com.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserRegistrationDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Email(message = "Ingrese un correo válido")
    @NotBlank(message = "El correo no puede estar vacío")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}
