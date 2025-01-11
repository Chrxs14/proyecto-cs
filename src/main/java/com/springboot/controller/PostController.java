package com.springboot.controller;

import com.springboot.dto.PostDTO;
import com.springboot.dto.PostTypeDTO;
import com.springboot.dto.UserDTO;
import com.springboot.exception.UserNotFoundException;
import com.springboot.service.PostService;
import com.springboot.service.PostTypeService;
import com.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private PostTypeService postTypeService;
    
    @Autowired
    private UserService userService;

    // Mostrar todos los posts
    @GetMapping
    public String getAllPosts(Model model) {
        List<PostDTO> posts = postService.getAllPosts();
        List<PostTypeDTO> postType = postTypeService.getAllPostTypes();
        model.addAttribute("postType", postType);
        model.addAttribute("posts", posts);
        return "post";
    }

    // Crear un nuevo post
    @PostMapping
    public String createPost(@ModelAttribute PostDTO postDTO, Authentication authentication, Model model) {
        logger.info("PostDTO: " + postDTO.toString());

        try {
            // Obtener el email del usuario autenticado
            String email = authentication.getName();

            // Buscar el usuario por su email (puedes usar tu UserService)
            UserDTO userDTO = userService.findByEmail(email);

            // Asociar el usuario autenticado con el post
            postDTO.setUser(userDTO);

            // Asegurarse de que los likes estén inicializados
            if (postDTO.getLikes() == null) {
                postDTO.setLikes(0);
            }

            // Crear la publicación
            PostDTO createdPost = postService.createPost(postDTO);

            // Agregar atributos al modelo para la vista
            model.addAttribute("postDTO", createdPost);
            model.addAttribute("posts", postService.getAllPosts());
            model.addAttribute("error", null);

            return "redirect:/posts";
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "User not found: " + e.getMessage());
            return "post";
        } catch (RuntimeException e) {
            logger.error("Unexpected error: " + e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "post";
        }
    }


    // Ver detalles de un post
    @GetMapping("/{id}")
    public String viewPost(@PathVariable UUID id, Authentication authentication, Model model) {
        PostDTO post = postService.getPostById(id);

        // Verifica si el usuario autenticado es el propietario
        boolean isOwner = post.getUser().getEmail().equals(authentication.getName());

        model.addAttribute("post", post);
        model.addAttribute("isOwner", isOwner);

        return "post-detail";
    }


    // Eliminar un post
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
    
    @PostMapping("/like/{id}")
    public String likePost(@PathVariable UUID id) {
        postService.incrementLikes(id);
        return "redirect:/posts";
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable UUID id, @RequestParam("content") String content, Authentication authentication, Model model) {
        try {
            // Obtener el email del usuario autenticado
            String email = authentication.getName();

            // Buscar al usuario por su email
            UserDTO userDTO = userService.findByEmail(email);

            // Agregar el comentario al post
            postService.addCommentToPost(id, userDTO.getId(), content);

            return "redirect:/posts";
        } catch (RuntimeException e) {
            logger.error("Failed to add comment: " + e.getMessage(), e);
            model.addAttribute("error", "Failed to add comment: " + e.getMessage());
            return "post";
        }
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PostTypeDTO.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                Long id = Long.valueOf(text);
                PostTypeDTO postTypeDTO = postTypeService.getPostTypeById(id);
                setValue(postTypeDTO);
            }
        });
        
        binder.registerCustomEditor(Integer.class, "likes", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(text));
                }
            }
        });
    }
}