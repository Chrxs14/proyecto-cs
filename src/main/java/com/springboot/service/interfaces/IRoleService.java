package com.springboot.service.interfaces;


import com.springboot.dto.RoleDTO;

import java.util.List;

public interface IRoleService {
    List<RoleDTO> getAllRoles();
    
    RoleDTO getRoleById(Long id);
    
    RoleDTO getRoleByName(String name);
}