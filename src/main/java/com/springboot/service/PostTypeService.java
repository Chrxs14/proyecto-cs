package com.springboot.service;

import com.springboot.dto.PostTypeDTO;
import com.springboot.entity.post.PostType;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.repository.post.PostTypeRepository;
import com.springboot.service.interfaces.IPostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostTypeService implements IPostTypeService {

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PostTypeDTO> getAllPostTypes() {
        return postTypeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PostTypeDTO getPostTypeById(Long id) {
        return postTypeRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post Type NOT FOUND with ID" + id));
    }

    public PostTypeDTO mapToDTO(PostType postType) {
        return PostTypeDTO.builder()
                .id(postType.getId())
                .name(postType.getName())
                .description(postType.getDescription())
                .build();
    }

    public PostType mapToEntity(PostTypeDTO postTypeDTO) {
        return PostType.builder()
                .id(postTypeDTO.getId())
                .name(postTypeDTO.getName())
                .description(postTypeDTO.getDescription())
                .build();
    }
}