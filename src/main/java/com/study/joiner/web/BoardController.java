package com.study.joiner.web;

import com.study.joiner.config.auth.LoginUser;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.domain.user.SocialUser;
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

    // 글 작성 -- 완
    @GetMapping("/user/write")
    public String userBoardWrite(Model model) {
        model.addAttribute("board", new BoardDto());
        return "view/board";
    }

    // 글 작성 처리 -- 완
    @PostMapping("/user/write")
    public String userBoardSave(@LoginUser SessionUser user, @ModelAttribute BoardDto boardDto) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        boardService.addBoard(socialUser, boardDto);
        return "redirect:/";
    }

    // 글 상세 정보 -- 완
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.getOtherUserBoard(id);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }

    // 내 작성 글 상세 정보 -- 완
    @GetMapping("/user/detail/{id}")
    public String detailUser(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        BoardDto boardDto = boardService.getUserBoard(id, socialUser);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }

    // 글 수정 조회
    @GetMapping("/edit/{id}")
    public String userBoardEdit(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        BoardDto boardDto = boardService.getUserEditBoard(id, socialUser);
        if(boardDto != null) {
            model.addAttribute("board", boardDto);
        }
        return "view/edit";
    }

    // 글 수정 처리
    @PutMapping("/edit/{id}")
    public String userBoardUpdate(@PathVariable Long id, @LoginUser SessionUser user, @ModelAttribute BoardDto boardDto) {
        SocialUser socialUser = userRepository.findByEmailFetchBL(user.getEmail()).orElseThrow();
        boardService.updateBoard(id, socialUser, boardDto);
        return "view/detail";
    }

    // 글 삭제 -- 완
    @DeleteMapping("/delete/{id}")
    public String userBoardDelete(@PathVariable Long id, @LoginUser SessionUser user) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        boardService.deleteBoard(id, socialUser);
        return "redirect:/";
    }

}
