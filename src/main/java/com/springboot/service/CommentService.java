package com.springboot.service;


import com.springboot.dto.CommentDTO;
import com.springboot.entity.user.User;
import com.springboot.entity.post.Comment;
import com.springboot.entity.post.Post;
import com.springboot.exception.CommentNotFoundException;
import com.springboot.repository.post.CommentRepository;
import com.springboot.service.interfaces.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CommentService implements ICommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    @Lazy
    private PostService postService;

    @Transactional
    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        logger.info("Creating a new comment...");

        Comment comment = mapToEntity(commentDTO);
        
        Comment savedComment = commentRepository.save(comment);
        logger.info("Comment created successfully with ID: {}", savedComment.getId());

        return mapToDTO(savedComment);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDTO getCommentById(UUID commentId) {
        logger.info("Fetching comment with ID: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        return mapToDTO(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getAllComments() {
        logger.info("Fetching all comments...");

        List<Comment> comments = commentRepository.findAll();
        logger.info("{} comments found.", comments.size());

        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getCommentsByPostId(UUID postId) {
        logger.info("Fetching comments for post with ID: {}", postId);

        List<Comment> comments = commentRepository.findByPostId(postId);
        logger.info("{} comments found for post with ID: {}", comments.size(), postId);

        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getCommentsByUserId(UUID userId) {
        logger.info("Fetching comments for user with ID: {}", userId);

        List<Comment> comments = commentRepository.findByUserId(userId);
        logger.info("{} comments found for user with ID: {}", comments.size(), userId);

        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDTO updateComment(UUID commentId, CommentDTO commentDTO) {
        logger.info("Updating comment with ID: {}", commentId);

        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        existingComment.setContent(commentDTO.getContent());
        existingComment.setLikes(commentDTO.getLikes());

        Comment updatedComment = commentRepository.save(existingComment);
        logger.info("Comment updated successfully with ID: {}", commentId);

        return mapToDTO(updatedComment);
    }

    @Transactional
    @Override
    public void deleteComment(UUID commentId) {
        logger.info("Deleting comment with ID: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        commentRepository.delete(comment);
        logger.info("Comment deleted successfully with ID: {}", commentId);
    }
    
    @Override
    public void incrementLikes(UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        
        CommentDTO commentDTO = mapToDTO(comment);
        
        commentDTO.incrementLikes();
        
        Comment commentEntity = mapToEntity(commentDTO);
        
        commentRepository.save(commentEntity);
    }

    public Comment mapToEntity(CommentDTO commentDTO) {
        logger.debug("Mapping CommentDTO to Comment entity...");

        User user = userService.mapToEntity(userService.getUserById(commentDTO.getUser().getId()));
        Post post = postService.mapToEntity(postService.getPostById(commentDTO.getPostId()));

        return Comment.builder()
                .user(user)
                .post(post)
                .content(commentDTO.getContent())
                .likes(commentDTO.getLikes())
                .build();
    }

    public CommentDTO mapToDTO(Comment comment) {
        logger.debug("Mapping Comment entity to CommentDTO...");

        return CommentDTO.builder()
                .id(comment.getId())
                .user(userService.mapToDTO(comment.getUser()))
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .likes(comment.getLikes())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}