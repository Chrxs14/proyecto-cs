package com.springboot.entity.user;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Size(max = 200, message = "Profile description cannot exceed 200 characters.")
    @Column(length = 200)
    private String bio;

    public Object stream() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}