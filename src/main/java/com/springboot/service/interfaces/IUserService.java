package com.springboot.service.interfaces;

import com.springboot.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    @Transactional
    UserDTO registerUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(UUID id);

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UUID id, UserDTO updatedUser);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void deleteUserById(UUID id);
}