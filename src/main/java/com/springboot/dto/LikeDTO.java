package com.springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDTO {
    
    private UUID id;
    
    @NotNull(message = "User cannot be null")
    private UserDTO user;
    
    private PostDTO post;
    
    private CommentDTO comment;
    
    @NotNull(message = "isForPost cannot be null")
    private boolean isForPost;
    
    public boolean isPostRequired() {
        return isForPost && post != null;
    }
    
    public boolean isCommentRequired() {
        return !isForPost && comment != null;
    }
}
