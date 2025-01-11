package com.springboot.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
    private UUID id;
    private UUID organizerId;
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int numberOfAttendees;
}
