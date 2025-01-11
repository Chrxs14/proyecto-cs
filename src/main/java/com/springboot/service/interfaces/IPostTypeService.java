package com.springboot.service.interfaces;



import com.springboot.dto.PostTypeDTO;

import java.util.List;

public interface IPostTypeService {
    List<PostTypeDTO> getAllPostTypes();
    
    PostTypeDTO getPostTypeById(Long id);
}