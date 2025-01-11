package com.springboot.service.interfaces;



import com.springboot.dto.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface ICommentService {

    // Obtener todos los comentarios
    List<CommentDTO> getAllComments();

    // Obtener comentarios por el ID de un post
    List<CommentDTO> getCommentsByPostId(UUID postId);

    // Obtener un comentario por su ID
    CommentDTO getCommentById(UUID commentId);

    // Crear un nuevo comentario
    CommentDTO createComment(CommentDTO commentDTO);

    // Actualizar un comentario existente
    CommentDTO updateComment(UUID commentId, CommentDTO commentDTO);

    // Eliminar un comentario por su ID
    void deleteComment(UUID commentId);

    // Obtener comentarios por el ID de un usuario
    List<CommentDTO> getCommentsByUserId(UUID userId);

    // Incrementar los "likes" de un comentario
    void incrementLikes(UUID commentId);
}