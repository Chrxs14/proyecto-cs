package com.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private UUID id;

    private UserDTO user;

    private UUID postId;

    @NotBlank(message = "Content cannot be empty or null") 
    private String content; 

    private Integer likes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", user=" + user +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public void incrementLikes() {
        this.likes += 1;
    }
}