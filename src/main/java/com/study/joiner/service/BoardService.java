package com.study.joiner.service;

import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PageImpl<BoardDto> getBoardList(Pageable pageable) {
        Page<Board> boardList = boardRepository.findAllDesc(pageable);
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boardList.getContent()) {
            BoardDto boardDto = new BoardDto(board);
            boardDtos.add(boardDto);
        }
        return new PageImpl<>(boardDtos, pageable, boardList.getTotalElements());
    }

    // 회원 전체 게시글 조회(페이징 적용) -- 완
    @Transactional(readOnly = true)
    public PageImpl<BoardDto> getUserBoardList(String email, Pageable pageable) {
        List<Board> boardList = userRepository.findByEmailFetchBL(email)
                .orElseThrow().getBoardList();

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), boardList.size());
        Page<Board> boardPagingList = new PageImpl<>(boardList.subList(start, end), pageable, boardList.size());
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boardPagingList.getContent()) {
            BoardDto boardDto = new BoardDto(board);
            boardDtos.add(boardDto);
        }
        return new PageImpl<>(boardDtos, pageable, boardPagingList.getTotalElements());
    }

    // 회원 특정 게시글 조회 -- 완
    @Transactional(readOnly = true)
    public BoardDto getUserBoard(Long id, SocialUser socialUser) {
        Board board = socialUser.getBoardList().get(Math.toIntExact(id)-1);
//        Board board = boardRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        BoardDto boardDto = new BoardDto(board);
        return boardDto;
    }

    // 다른 회원 글 상세 조회 -- 완
    @Transactional(readOnly = true)
    public BoardDto getOtherUserBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        BoardDto boardDto = new BoardDto(board.get());
        return boardDto;
    }

    // 회원 글 수정시 조회 -- 완
    @Transactional(readOnly = true)
    public BoardDto getUserEditBoard(Long id, SocialUser socialUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        BoardDto boardDto = new BoardDto(board);
        if(board.getSocialUser().getId().equals(socialUser.getId())) {
            return boardDto;
        }
        return null;
    }

    // 게시글 수정(변경 감지) -- 완
    @Transactional
    public void updateBoard(Long id, SocialUser socialUser, BoardDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        if(board.getSocialUser().getId().equals(socialUser.getId())) {
            board.edit(boardDto);
        }
    }

    // 게시글 삭제 -- 완
    @Transactional
    public void deleteBoard(Long id, SocialUser socialUser) {
        Board board = boardRepository.findById(id).orElseThrow();
        if(board.getSocialUser().getId().equals(socialUser.getId())) {
            socialUser.getBoardList().remove(board);
            boardRepository.delete(board);
        }
    }
}

