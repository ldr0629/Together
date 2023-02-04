package com.study.joiner.web;

import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.Role;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.web.dto.BoardDto;
import groovy.util.logging.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        boardRepository.deleteAll();
    }

    @Test
    @Transactional
    public void boardSave() {
        // given
        SocialUser user = SocialUser.builder()
                        .name("leedero01")
                        .email("dleofh01@hs.ac.kr")
                        .picture("img.jpg")
                        .role(Role.USER).
                        build();

        userRepository.save(user);

        String title = "spring";
        String content = "how to spring mvc";
        String contact = "kakaotalk";
        String duration = "3";
        String date = "2023/05/04";
        String recruit = "10";
        String progress = "study";
        String recruitNum = "5";
        String skill = "Java";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .contactWay(contact)
                .duration(duration)
                .date(date)
                .postRecruit(recruit)
                .progressWay(progress)
                .recruitNum(recruitNum)
                .skill(skill)
                .build();

        board.setUser(user);
        boardRepository.save(board);

        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        Board savedBoard = boardList.get(0);
        assertThat(savedBoard.getTitle()).isEqualTo(title);
        assertThat(savedBoard.getContent()).isEqualTo(content);
        assertThat(savedBoard.getSocialUser().getEmail()).isEqualTo("dleofh01@hs.ac.kr");
    }

    @Test
    @Transactional
    public void boardUpdate() {
        // given
        SocialUser user = SocialUser.builder()
                .name("leedero01")
                .email("dleofh01@hs.ac.kr")
                .picture("img.jpg")
                .role(Role.USER).
                build();

        userRepository.save(user);

        String title = "spring";
        String content = "how to spring mvc";
        String contact = "kakaotalk";
        String duration = "3";
        String date = "2023/05/04";
        String recruit = "10";
        String progress = "study";
        String recruitNum = "5";
        String skill = "Java";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .contactWay(contact)
                .duration(duration)
                .date(date)
                .postRecruit(recruit)
                .progressWay(progress)
                .recruitNum(recruitNum)
                .skill(skill)
                .build();

        board.setUser(user);
        boardRepository.save(board);

        BoardDto boardDto = new BoardDto(board);
        boardDto.setTitle("Java Programming");
        boardDto.setContent("deal with java functional programming");
        board.edit(boardDto);

        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        Board savedBoard = boardList.get(0);
        assertThat(savedBoard.getTitle()).isNotEqualTo(title);
        assertThat(savedBoard.getContent()).isNotEqualTo(content);
        assertThat(savedBoard.getSocialUser().getEmail()).isEqualTo("dleofh01@hs.ac.kr");
    }

    @Test
    @Transactional
    public void boardDelete() {
        // given
        SocialUser user = SocialUser.builder()
                .name("dleofh01")
                .email("leedero01@naver.com")
                .picture("img.jpg")
                .role(Role.USER).
                build();

        userRepository.save(user);

        String title = "spring";
        String content = "how to spring mvc";
        String contact = "kakaotalk";
        String duration = "3";
        String date = "2023/05/04";
        String recruit = "10";
        String progress = "study";
        String recruitNum = "5";
        String skill = "Java";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .contactWay(contact)
                .duration(duration)
                .date(date)
                .postRecruit(recruit)
                .progressWay(progress)
                .recruitNum(recruitNum)
                .skill(skill)
                .build();

        board.setUser(user);
        boardRepository.save(board);

        // when
        Board savedBoard = boardRepository.findById(board.getId()).orElseThrow();
        boardRepository.delete(board);
        List<Board> boardList = boardRepository.findAll();

        // then
        assertThat(boardList.size()).isEqualTo(0);
    }
}
