package com.study.joiner.web;

import com.study.joiner.config.auth.LoginUser;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.BoardRepository;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.service.BoardService;
import com.study.joiner.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 글 목록
    @GetMapping("/list")
    public String index(@LoginUser SessionUser user, Model model,
                        @RequestParam(required = false) String keyword,
                        @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        SocialUser socialUser = userRepository.findById(user.getId()).orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));
        Page<Board> paging;

        paging = keyword == null ? boardService.getList(pageable) : boardService.getSearchList(pageable, keyword);

        model.addAttribute("boards", socialUser.getBoardList());
        model.addAttribute("user", user);
        model.addAttribute("paging", paging);

        return "view/list";
    }

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
    public String detail(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        BoardDto boardDto = boardService.getOtherUserBoard(id);
        model.addAttribute("user", user);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }

    // 글 수정 조회
    @GetMapping("/edit/{id}")
    public String userBoardEdit(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        BoardDto boardDto = boardService.getEditBoard(id, user.getEmail());
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
        boardService.deleteBoard(id, user.getEmail());
        return "redirect:/";
    }
}
