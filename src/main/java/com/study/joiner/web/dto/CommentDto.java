package com.study.joiner.web.dto;

import com.study.joiner.domain.user.Comment;
import com.study.joiner.domain.user.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@RequiredArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private String content;
    private UserResponseDto userResponseDto;
//    private SocialUser user;

    public CommentDto(Long id, String content, UserResponseDto userResponseDto) {
        this.id = id;
        this.content = content;
        this.userResponseDto = userResponseDto;
//        this.user = entity.getUser();
    }
}
