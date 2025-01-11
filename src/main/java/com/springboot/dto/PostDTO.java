package com.springboot.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private UUID id;
    private UserDTO user;
    private String content;
    private Integer likes;
    private PostTypeDTO postType;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private List<CommentDTO> comments;
}