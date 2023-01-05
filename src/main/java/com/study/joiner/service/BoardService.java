package com.study.joiner.service;

import com.study.joiner.domain.user.Board;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Long;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    // readOnly
    // for문 말고 람다,스트림(stream)
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = boardList.stream().map(BoardDto::new)
                .collect(Collectors.toList());
//        for(Board board : boardList) {
//            BoardDto boardDto = BoardDto.builder()
//                    .id(board.getId())
//                    .study_project(board.getStudy_project())
//                    .person_num(board.getPerson_num())
//                    .online_offline(board.getOnline_offline())
//                    .duration(board.getDuration())
//                    .skill(board.getSkill())
//                    .date(board.getDate())
//                    .calling(board.getCalling())
//                    .title(board.getTitle())
//                    .input_content(board.getInput_content())
//                    .createdDate(board.getCreatedDate())
//                    .build();
//            boardDtoList.add(boardDto);
//        }
        return boardDtoList;
    }
//    @Transactional
//    public List<BoardDto> getBoardListById(Long id) {
//        List<Board> boardList = boardRepository.findAllById(id); 사용자 id로 보드리스트 가져와서 담아야함.
//        List<BoardDto> boardDtoList = boardList.stream().map(BoardDto::new)
//                .collect(Collectors.toList());
//        return boardDtoList;
//    }

    @Transactional
    public BoardDto getPost(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .study_project(board.getStudy_project())
                .person_num(board.getPerson_num())
                .online_offline(board.getOnline_offline())
                .duration(board.getDuration())
                .skill(board.getSkill())
                .date(board.getDate())
                .calling(board.getCalling())
                .title(board.getTitle())
                .input_content(board.getInput_content())
                .createdDate(board.getCreatedDate())
                .build();
        return boardDto;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}

