package com.springboot.service;

import com.springboot.dto.UserDTO;
import com.springboot.entity.user.Role;
import com.springboot.entity.user.User;
import com.springboot.entity.user.Profile;
import com.springboot.exception.UserNotFoundException;
import com.springboot.repository.user.ProfileRepository;
import com.springboot.repository.user.UserRepository;
import com.springboot.service.interfaces.IUserService;
import com.springboot.util.AppConstants;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Primary
public class UserService implements IUserService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private final PasswordEncoder passwordEncoder2;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ProfileRepository profileRepository;

	public UserService(PasswordEncoder passwordEncoder2) {
		this.passwordEncoder2 = passwordEncoder2;
	}

	// TO DO Change this to auth method
	public UserDTO getCurrentUserInSession() {
		return this.getAllUsers().get(0);
	}

	public String encodePassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		return null;
	}

	@Transactional(readOnly = true)
	public UserDTO findByEmail(String email) {
		return userRepository.findByEmail(email)
				.map(this::mapToDTO)
				.orElseThrow(() -> new UserNotFoundException(email));
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public User authenticateUser(String email, String password) {
		logger.info("Intentando autenticar al usuario con email: {}", email);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> {
					logger.warn("Usuario no encontrado con email: {}", email);
					return new UserNotFoundException("Usuario no encontrado");
				});

		if (!passwordEncoder.matches(password, user.getPassword())) {
			logger.warn("La contraseña proporcionada no coincide para el usuario con email: {}", email);
			throw new IllegalArgumentException("Contraseña incorrecta");
		}

		logger.info("Usuario autenticado exitosamente: {}", email);
		return user;
	}




	@Transactional(readOnly = true)
	@Override
	public UserDTO getUserById(UUID id) {
		return userRepository.findById(id)
				.map(this::mapToDTO)
				.orElseThrow(() -> new UserNotFoundException(id));
	}

	@Transactional
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		if(userDTO.getRole() == null) {
			userDTO.setRole(roleService.getRoleByName(AppConstants.DEFAULT_ROLE_NAME));
		}
		User user = mapToEntity(userDTO);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User savedUser = userRepository.save(user);

		createProfileForUser(savedUser);
		return mapToDTO(savedUser);
	}

	@Transactional
	@Override
	public UserDTO updateUser(UUID id, UserDTO updatedUserDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
		User updatedUser = userRepository.save(existingUser);
		return mapToDTO(updatedUser);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> {
					logger.warn("Usuario no encontrado con email:" + email+" - mail");
					return new UserNotFoundException("Usuario no encontrado");
				});
//        if(user.isEmpty) {
//            throw new UsernameNotFoundException("Usuario o password inválidos");
//        }
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(AppConstants.DEFAULT_ROLE_NAME)));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(roleService.getAllRoles().toString())).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public void deleteUserById(UUID id) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		profileRepository.deleteByUserId(id);
		userRepository.deleteById(id);
	}

	private void createProfileForUser(User user) {
		Profile profile = Profile.builder()
				.user(user)
				.bio(AppConstants.DEFAULT_BIO)
				.build();
		profileRepository.save(profile);
	}

	public UserDTO mapToDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.role(roleService.mapToDTO(user.getRole()))
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.build();
	}

	public User mapToEntity(UserDTO userDTO) {
		return User.builder()
				.id(userDTO.getId())  // Null for new users
				.name(userDTO.getName())
				.email(userDTO.getEmail())
				.password(userDTO.getPassword())  // Encrypt password if needed
				.role(roleService.mapToEntity(userDTO.getRole()))
				.build();
	}

	public UserDTO mapToUserDTO(UserDTO UserRegistrationDTO) {
		return UserDTO.builder()
				.name(UserRegistrationDTO.getName())
				.email(UserRegistrationDTO.getEmail())
				.role(roleService.getRoleByName(AppConstants.DEFAULT_ROLE_NAME))
				.password(UserRegistrationDTO.getPassword())
				.build();
	}
}