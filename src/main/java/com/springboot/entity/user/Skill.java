package com.springboot.entity.user;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "skill", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotNull(message = "Name cannot be null.")
    @NotEmpty(message = "Name cannot be empty.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;
}
