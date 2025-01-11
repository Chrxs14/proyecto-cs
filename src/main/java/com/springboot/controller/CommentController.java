package com.springboot.controller;

import com.springboot.dto.CommentDTO;
import com.springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
    }

    @PostMapping("/{commentId}/like")
    public void likeComment(@PathVariable UUID commentId) {
        commentService.incrementLikes(commentId);
    }
}