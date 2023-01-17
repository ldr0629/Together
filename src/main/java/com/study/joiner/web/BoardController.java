package com.study.joiner.web;

import com.mysql.cj.Session;
import com.study.joiner.config.auth.LoginUser;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.service.BoardService;
import com.study.joiner.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 글 작성 -- 완
    @GetMapping("/user/write")
    public String boardWrite() {
        return "view/board";
    }

    // 글 작성 처리 -- 완
    @PostMapping("/user/write")
    public String saveBoard(@LoginUser SessionUser user, @ModelAttribute BoardDto boardDto) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        boardService.addBoard(socialUser, boardDto);
        return "redirect:/";
    }

    // 글 상세 정보
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        Board board = boardRepository.findById(id).orElseThrow();
        BoardDto boardDto = boardService.getOtherUserBoard(id);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }

    // 글 수정 조회
    @GetMapping("/edit/{id}")
    public String userBoardEdit(@LoginUser SessionUser user, Model model) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        BoardDto boardDto = boardService.getUserBoard(socialUser.getId(), socialUser);
        model.addAttribute("board", boardDto);
        return "view/edit";
    }

    // 글 수정 처리
    @PutMapping("/edit/{id}")
    public String update(@LoginUser SessionUser user, @ModelAttribute BoardDto boardDto) {
        SocialUser socialUser = userRepository.findByEmailFetchBL(user.getEmail()).orElseThrow();
        boardService.updateBoard(boardDto.getId(), socialUser, boardDto);
        return "view/detail";
    }

    // 글 삭제 -- 완
    @DeleteMapping("/delete/{id}")
    public String delete(@LoginUser SessionUser user, @ModelAttribute BoardDto boardDto) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        boardService.deleteBoard(boardDto.getId(), socialUser);
        return "redirect:/";
    }
}
