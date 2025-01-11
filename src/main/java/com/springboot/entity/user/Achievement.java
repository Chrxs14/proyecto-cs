package com.springboot.entity.user;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "achivement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @NotNull(message = "Description cannot be null.")
    @NotEmpty(message = "Description cannot be empty.")
    @Size(max = 100, message = "Description cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String description;
    
    @Column(nullable = false)
    private Date achived_date;
}