package com.study.joiner.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequestDto {
    private String nickName;
    private String content;

    public UserUpdateRequestDto(String nickName, String content) {
        this.nickName = nickName;
        this.content = content;
    }
}
