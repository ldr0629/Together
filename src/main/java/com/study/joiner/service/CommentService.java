package com.study.joiner.service;


import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.Comment;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.CommentRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.web.dto.CommentDto;
import com.study.joiner.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(Long id, CommentDto commentRequestDto, String email) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시물이 삭제되었거나 존재하지 않습니다."));
        SocialUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .build();
        comment.mappingBoardAndUser(board, user);
        commentRepository.save(comment);
    }

    // 변경 감지
    @Transactional
    public void update(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));
        comment.update(commentDto.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));
        commentRepository.delete(comment);
    }
}
