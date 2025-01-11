package com.springboot.service;


import com.springboot.dto.RoleDTO;
import com.springboot.entity.user.Role;
import com.springboot.exception.RoleNotFoundException;
import com.springboot.repository.user.RoleRepository;
import com.springboot.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDTO getRoleByName(String name) {
        return roleRepository.findByName(name)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }

    public RoleDTO mapToDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role mapToEntity(RoleDTO roleDTO) {
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .build();
    }
}
