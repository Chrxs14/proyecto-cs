package com.springboot.repository.post;

import com.springboot.entity.post.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {
    Optional<PostType> findByName(String name);
}