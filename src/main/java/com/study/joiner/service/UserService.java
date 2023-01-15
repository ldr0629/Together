package com.study.joiner.service;

import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.web.dto.UserResponseDto;
import com.study.joiner.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    // 내 정보 수정 -- 완
    @Transactional
    public UserResponseDto update(String email, UserUpdateRequestDto requestDto) {
        SocialUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.userUpdate(requestDto.getNickName(), requestDto.getContent());
        return new UserResponseDto(user);
    }

    // 내 정보 조회 -- 완
    @Transactional(readOnly = true)
    public UserResponseDto getInfo(String email) {
        SocialUser entity = userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        return new UserResponseDto(entity);
    }

    // 회원 탈퇴 -- 완
    @Transactional
    public void delete(SocialUser socialUser) {
        userRepository.delete(socialUser);
    }
}
