package com.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    @NotNull(message = "El rol no puede estar vacío.")
    private RoleDTO role;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO(UUID id, @NotNull(message = "Name cannot be null.") @NotEmpty(message = "Name cannot be empty.") @Size(max = 100, message = "Name cannot exceed 100 characters.") String name, @NotNull(message = "Email cannot be null.") @NotEmpty(message = "Email cannot be empty.") @Email(message = "Email should be valid.") String email) {
    }
}