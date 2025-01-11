package com.springboot.entity.event;

import com.springboot.entity.user.User;
import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "organizer_id", referencedColumnName = "id", nullable = false)
    private User organizer;
    
    @NotNull(message = "Title cannot be null.")
    @NotEmpty(message = "Title cannot be empty.")
    @Size(max = 100, message = "Title cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String title;
    
    @NotNull(message = "Description cannot be null.")
    @NotEmpty(message = "Description cannot be empty.")
    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    @Column(nullable = false, length = 500)
    private String description;
    
    @NotNull(message = "Date cannot be null.")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Location cannot be null.")
    @NotEmpty(message = "Location cannot be empty.")
    @Size(max = 200, message = "Location cannot exceed 200 characters.")
    @Column(nullable = false, length = 200)
    private String location;

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date =  LocalDateTime.now();
        }
    }
}
