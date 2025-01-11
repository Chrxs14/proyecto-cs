package com.springboot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserAchivementDTO {
    private Long id;
    private String Description;
    private LocalDateTime date;
}