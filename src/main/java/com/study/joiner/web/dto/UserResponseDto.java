package com.study.joiner.web.dto;

import com.study.joiner.domain.user.SocialUser;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String picture;

    public UserResponseDto(SocialUser entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.picture = entity.getPicture();
    }
}
