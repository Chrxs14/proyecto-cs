package com.springboot.entity.post;

import com.springboot.entity.user.User;
import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_type_id", referencedColumnName = "id", nullable = false)
    private PostType type;

    @NotNull(message = "Content cannot be null.")
    @NotEmpty(message = "Content cannot be empty.")
    @Size(max = 500, message = "Content cannot exceed 500 characters.")
    @Column(nullable = false, length = 500)
    private String content;

    @Min(value = 0, message = "Likes must be a non-negative number.")
    private Integer likes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
