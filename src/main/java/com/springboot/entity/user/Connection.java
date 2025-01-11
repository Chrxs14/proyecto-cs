package com.springboot.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "connection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_connection_id", referencedColumnName = "id", nullable = false)
    private User userConnection;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private ConnectionStatus status;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
