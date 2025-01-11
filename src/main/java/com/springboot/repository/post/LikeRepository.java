package com.springboot.repository.post;


import com.springboot.entity.user.User;
import com.springboot.entity.post.Like;
import com.springboot.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    Optional<Like> findByUserAndPost(User user, Post post);

    List<Like> findByPost(Post post);
}