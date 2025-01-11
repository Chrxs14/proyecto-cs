package com.springboot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeDTO {
    private Long id;
    private String name;
    private String description;
}