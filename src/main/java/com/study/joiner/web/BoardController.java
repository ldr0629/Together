package com.study.joiner.web;


import com.study.joiner.service.BoardService;
import com.study.joiner.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        return "view/board";
    }
//
//    @GetMapping("/post")
//    public String post() {
//        return "board/post.html";
//    }
//
//    @PostMapping("/post")
//    public String write(BoardDto boardDto) {
//        boardService.savePost(boardDto);
//        return "redirect:/";
//    }
//
//    @GetMapping("/post/{id}")
//    public String detail(@PathVariable("id") Long id, Model model) {
//        BoardDto boardDto = boardService.getPost(id);
//        model.addAttribute("post", boardDto);
//        return "board/detail.html";
//    }
//
//    @GetMapping("/post/edit/{id}")
//    public String edit(@PathVariable("id") Long id, Model model) {
//        BoardDto boardDto = boardService.getPost(id);
//        model.addAttribute("post", boardDto);
//        return "board/edit.html";
//    }
//
//    @PutMapping("/post/edit/{id}")
//    public String update(BoardDto boardDto) {
//        boardService.savePost(boardDto);
//        return "redirect:/";
//    }
//
//    @DeleteMapping("/post/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        boardService.deletePost(id);
//        return "redirect:/";
//    }
}
