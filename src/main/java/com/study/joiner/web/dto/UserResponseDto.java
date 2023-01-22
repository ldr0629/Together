package com.study.joiner.web.dto;

import com.study.joiner.domain.user.SocialUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class UserResponseDto {
    private Long id;

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;

    private String picture;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
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
