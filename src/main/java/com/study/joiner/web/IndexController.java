package com.study.joiner.web;

import com.study.joiner.config.auth.LoginUser;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.service.UserService;
import com.study.joiner.web.dto.UserResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final UserService userService;
    private final HttpSession httpSession;

    // 메인페이지
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
//        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    // 내 정보 수정
    @GetMapping("/user/update/{id}")
    public String userUpdate(@PathVariable Long id, Model model) {
        UserResponseDto dto = userService.findById(id);
        model.addAttribute("user", dto);
        return "user-update";
    }
}
