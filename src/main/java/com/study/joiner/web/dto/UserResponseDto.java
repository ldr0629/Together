package com.study.joiner.web.dto;

import com.study.joiner.domain.user.SocialUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
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
