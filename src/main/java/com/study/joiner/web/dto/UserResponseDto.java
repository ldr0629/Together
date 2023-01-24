package com.study.joiner.web.dto;

import com.study.joiner.domain.user.SocialUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private String nickName;
    private String content;
    private LocalDateTime createdDate;
    private String date;

    public UserResponseDto(SocialUser entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.nickName = entity.getNickName();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.date = this.createdDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
