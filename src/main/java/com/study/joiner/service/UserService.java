package com.study.joiner.service;

import com.study.joiner.domain.user.SocialUser;
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

    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto) {
        SocialUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.update(requestDto.getName(), requestDto.getPicture());
        return id;
    }

    public UserResponseDto findById(Long id) {
        SocialUser entity = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new UserResponseDto(entity);
    }
}
