package com.springboot.entity.post;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "value")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Name cannot be null.")
    @NotEmpty(message = "Name cannot be empty.")
    @Size(max = 20, message = "Name cannot exceed 20 characters.")
    @Column(nullable = false, length = 20)
    private String name;
    
    @NotNull(message = "Description cannot be null.")
    @NotEmpty(message = "Description cannot be empty.")
    @Size(max = 100, message = "Description cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String description;
}
