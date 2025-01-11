package com.springboot.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private ProfileDTO profile;
    private List<PostDTO> posts;
    private List<SkillDTO> skills;
    private List<AchievementDTO> achivements;
    private List<EventDTO> events;
}