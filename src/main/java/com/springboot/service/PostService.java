package com.springboot.service;

import com.springboot.dto.CommentDTO;
import com.springboot.dto.PostDTO;
import com.springboot.entity.post.Post;
import com.springboot.exception.PostNotFoundException;
import com.springboot.repository.post.PostRepository;
import com.springboot.service.interfaces.IPostService;
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
public class PostService implements IPostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostTypeService postTypeService;
    
    @Autowired
    @Lazy
    private CommentService commentService;

    @Transactional
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        logger.info("Creating a new post...");
        
        Post post = mapToEntity(postDTO);

        Post savedPost = postRepository.save(post);
        logger.info("Post created successfully with ID: {}", savedPost.getId());

        return mapToDTO(savedPost);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDTO getPostById(UUID postId) {
        logger.info("Fetching post with ID: {}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return mapToDTO(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getAllPosts() {
        logger.info("Fetching all posts...");

        List<Post> posts = postRepository.findAll();
        logger.info("{} posts found.", posts.size());

        return posts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getPostsByUserId(UUID userId) {
        logger.info("Fetching posts for user with ID: {}", userId);

        List<Post> posts = postRepository.findByUserId(userId);

        logger.info("{} posts found for user with ID: {}", posts.size(), userId);

        return posts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostDTO updatePost(UUID postId, PostDTO postDTO) {
        logger.info("Updating post with ID: {}", postId);

        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        existingPost.setContent(postDTO.getContent());
        existingPost.setLikes(postDTO.getLikes());

        Post updatedPost = postRepository.save(existingPost);
        logger.info("Post updated successfully with ID: {}", postId);

        return mapToDTO(updatedPost);
    }

    @Transactional
    @Override
    public void deletePost(UUID postId) {
        logger.info("Deleting post with ID: {}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        postRepository.delete(post);
        logger.info("Post deleted successfully with ID: {}", postId);
    }
    
    @Transactional
    @Override
    public void incrementLikes(UUID postId) {
        PostDTO post = getPostById(postId);

        post.setLikes(post.getLikes() + 1);

        postRepository.save(this.mapToEntity(post));
    }

    public void addCommentToPost(UUID postId, UUID userId, String content) {
        CommentDTO comment = new CommentDTO();
        comment.setContent(content);
        comment.setUser(userService.getUserById(userId));
        comment.setPostId(postId);
        comment.setLikes(0);
        logger.info("Adding comment for postId: " + postId + ", userId: " + userId + ", content: " + content);

        commentService.createComment(comment);
    }

    public Post mapToEntity(PostDTO postDTO) {
        logger.debug("Mapping PostDTO to Post entity...");
        return Post.builder()
                .id(postDTO.getId())
                .user(userService.mapToEntity(userService.getUserById(postDTO.getUser().getId())))
                .content(postDTO.getContent())
                .likes(postDTO.getLikes())
                .type(postTypeService.mapToEntity(postDTO.getPostType()))
                .build();
    }

    @Transactional(readOnly = true)
    public PostDTO mapToDTO(Post post) {
        logger.debug("Mapping Post entity to PostDTO...");

        List<CommentDTO> comments = commentService.getCommentsByPostId(post.getId());

        return PostDTO.builder()
                .id(post.getId())
                .user(userService.mapToDTO(post.getUser()))
                .content(post.getContent())
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .updateAt(post.getUpdatedAt())
                .postType(postTypeService.mapToDTO(post.getType()))
                .comments(comments)
                .build();
    }
}