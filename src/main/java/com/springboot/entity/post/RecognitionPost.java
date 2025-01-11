package com.springboot.entity.post;

import com.springboot.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "recognition_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecognitionPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private User receiver;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "value_id", referencedColumnName = "id", nullable = false)
    private Value value;
}
