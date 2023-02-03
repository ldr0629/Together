package com.study.joiner.service;

import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.Long;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 등록 -- 완
    @Transactional
    public void addBoard(SocialUser user, BoardDto boardDto) {
        Board board = boardDto.toEntity();
        board.setUser(user);
        boardRepository.save(board);
    }

    // 전체 게시글 조회 -- 완
    @Transactional(readOnly = true)
    public Page<BoardDto> getBoardList(int pageNum, Pageable pageable) {
        pageable = PageRequest.of(pageNum, pageable.getPageSize());
        Page<Board> boardList = boardRepository.findAllDesc(pageable);
        Page<BoardDto> boardDtos = boardList.map(BoardDto::new);
        return boardDtos;
    }

    @Transactional(readOnly = true)
    public List<BoardDto> getBoardList() {
        return boardRepository.findAllDesc().stream()
                .map(BoardDto::new)
                .collect(Collectors.toList());
    }

    // 회원 전체 게시글 조회(페이징 적용) -- 완
    @Transactional(readOnly = true)
    public Page<BoardDto> getUserBoardList(String email, int pageNum, Pageable pageable) {
        PageRequest page = PageRequest.of(pageNum, pageable.getPageSize());
        Page<Board> boardList  = boardRepository.findAllByEmail(email, page);
        return boardList.map(BoardDto::new);
    }

    // 다른 회원 글 상세 조회 -- 완
    @Transactional(readOnly = true)
    public BoardDto getOtherUserBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return new BoardDto(board.get());
    }

    public Page<Board> getList(Pageable pageable) {
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return boardRepository.findAllBy(pageable);
    }

    public Page<Board> getSearchList(Pageable pageable, String keyword) {
        return boardRepository.findByTitleContaining(keyword, pageable);
    }

    // 회원 글 조회 -- 완
    @Transactional(readOnly = true)
    public BoardDto getUserBoard(Long id, SocialUser socialUser) {
        Board board = socialUser.getBoardList().get(Math.toIntExact(id)-1);
        return new BoardDto(board);
    }

    // 회원 메인페이지 상세 글 조회
    @Transactional(readOnly = true)
    public BoardDto getEditBoard(Long id, String email) {
        SocialUser socialUser = userRepository.findByEmail(email).orElseThrow();
        Board board = boardRepository.findById(id).orElseThrow();
        if(board.getSocialUser().getId().equals(socialUser.getId())) {
            new BoardDto(board);
        }
        return null;
    }

    // 게시글 수정(변경 감지) -- 완
    @Transactional
    public void updateBoard(Long id, SocialUser socialUser, BoardDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        board.edit(boardDto);
        boardRepository.save(board);
    }

    // 게시글 삭제 -- 완
    @Transactional
    public void deleteBoard(Long id, String email) {
        SocialUser socialUser = userRepository.findByEmail(email).orElseThrow();
        Board board = boardRepository.findById(id).orElseThrow();
        if(board.getSocialUser().getId().equals(socialUser.getId())) {
            socialUser.getBoardList().remove(board);
            boardRepository.delete(board);
        }
    }
}

