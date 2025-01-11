package com.springboot.repository.post;

import com.springboot.entity.post.RecognitionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecognitionPostRepository extends JpaRepository<RecognitionPost, UUID> {
}
