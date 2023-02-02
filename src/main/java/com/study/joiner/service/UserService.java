package com.study.joiner.service;

import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.web.dto.UserResponseDto;
import com.study.joiner.web.dto.UserUpdateRequestDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private HttpSession httpSession;

    // 내 정보 수정 -- 완
    @Transactional
    public UserResponseDto update(String email, UserUpdateRequestDto requestDto) {
        SocialUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 중복 회원이 아니라면 수정 가능
        if(!checkNicknameDuplication(requestDto.getNickName())) {
            user.userUpdate(requestDto.getNickName(), requestDto.getContent());
            userRepository.save(user);
            return new UserResponseDto(user);
        } else {
            return new UserResponseDto(user);
        }
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
        httpSession.removeAttribute("socialUser");
    }

    // 닉네임 중복 여부 확인
    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickName) {
        return userRepository.existsByNickName(nickName);
    }
}
