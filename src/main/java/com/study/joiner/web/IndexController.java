package com.study.joiner.web;

import com.mysql.cj.Session;
import com.study.joiner.config.auth.LoginUser;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.SocialUser;
import com.study.joiner.repository.UserRepository;
import com.study.joiner.service.BoardService;
import com.study.joiner.service.UserService;
import com.study.joiner.web.dto.BoardDto;
import com.study.joiner.web.dto.UserResponseDto;
import com.study.joiner.web.dto.UserUpdateRequestDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final UserService userService;
    private final BoardService boardService;
    private final UserRepository userRepository;

    // 메인페이지 -- 완
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user,
                        @PageableDefault(size = 10) Pageable pageable) {
        Page<BoardDto> boardList = boardService.getBoardList(pageable);
        int startPage = Math.max(1, boardList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boardList.getTotalPages(), boardList.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardList", boardList);
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }

    // 내 작성 글 목록 조회
    @GetMapping("/user/board")
    public String userBoardList(@LoginUser SessionUser user, Model model,
                                @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardDto> boardList = boardService.getUserBoardList(user.getEmail(), pageable);
        int startPage = Math.max(1, boardList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boardList.getTotalPages(), boardList.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userBoardList", boardList);
        model.addAttribute("user", user);
        return "view/userBoard";
    }

    // 내 작성 글 조회 -- 완
     @GetMapping("/user/board/{id}")
     public String userBoard(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        SocialUser socialUser = userRepository.findByEmailFetchBL(user.getEmail()).orElseThrow();
        BoardDto boardDto = boardService.getUserBoard(id, socialUser);
        model.addAttribute("userBoard", boardDto);
        return "view/detail";
    }

    // 내 정보 수정 조회 -- 완
    @GetMapping("/user/update")
    public String userUpdate(@LoginUser SessionUser user, Model model) {
        UserResponseDto userResponseDto = userService.getInfo(user.getEmail());
        model.addAttribute("user", userResponseDto);
        return "view/setting";
    }

    // 내 정보 수정 처리 -- 완
    @PostMapping("/user/update")
    public String userUpdateComplete(@LoginUser SessionUser user, @Valid @ModelAttribute UserUpdateRequestDto requestDto,
            BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            // 회원가입 실패 시 입력 데이터 값 유지
            model.addAttribute("userResponseDto", requestDto);

            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
            }
            return "redirect:/";
        }

        UserResponseDto userResponseDto = userService.update(user.getEmail(), requestDto);
        model.addAttribute("user", userResponseDto);
        return "view/setting";
    }

    // 회원 탈퇴 -- 완
    @DeleteMapping("/user/delete")
    public String userDelete(@LoginUser SessionUser user) {
        SocialUser socialUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException());
        userService.delete(socialUser);
        return "redirect:/";
    }
}
