package com.springboot.repository.post;

import com.springboot.entity.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
     List<Comment> findByPostId(UUID postId);
     List<Comment> findByUserId(UUID userId);
}