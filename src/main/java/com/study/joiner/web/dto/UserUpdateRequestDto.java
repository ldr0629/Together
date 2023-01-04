package com.study.joiner.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String picture;

    @Builder
    public UserUpdateRequestDto(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }
}
