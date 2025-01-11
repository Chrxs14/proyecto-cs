package com.springboot.entity.post;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Post type name cannot be null.")
    @NotEmpty(message = "Post type name cannot be empty.")
    @Size(max = 50, message = "Post type name cannot exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull(message = "Description cannot be null.")
    @NotEmpty(message = "Description cannot be empty.")
    @Size(max = 100, message = "Description cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String description;
}
