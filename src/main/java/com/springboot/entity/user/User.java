package com.springboot.entity.user;


import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull(message = "Name cannot be null.")
	@NotEmpty(message = "Name cannot be empty.")
	@Size(max = 100, message = "Name cannot exceed 100 characters.")
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull(message = "Email cannot be null.")
	@NotEmpty(message = "Email cannot be empty.")
	@Email(message = "Email should be valid.")
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@NotNull(message = "Password cannot be null.")
	@NotEmpty(message = "Password cannot be empty.")
	@Size(min = 8, max = 128, message = "Password must be at least 8 characters long.")
	@Column(nullable = false)
	private String password;

	@ManyToOne(fetch = FetchType.EAGER) // Relación con Role
	@JoinColumn(name = "role_id", nullable = false) // Mapea la FK 'role_id'
	private Role role;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}