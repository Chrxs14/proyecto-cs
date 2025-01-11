package com.springboot.exception;

import java.util.UUID;

public class NotLikesFoundForUserOnPost extends RuntimeException {
    public NotLikesFoundForUserOnPost(UUID userId, UUID postId) {
        super("No likes found on post: " + postId + " for user: " + userId);
    }
}