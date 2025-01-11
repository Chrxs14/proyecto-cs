package com.springboot.service.interfaces;



import com.springboot.dto.PostDTO;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    PostDTO createPost(PostDTO postDTO);
    PostDTO getPostById(UUID postId);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostsByUserId(UUID userId);
    PostDTO updatePost(UUID postId, PostDTO postDTO);
    void deletePost(UUID postId);
    void incrementLikes(UUID postId);
    void addCommentToPost(UUID postId, UUID userId, String content);
}