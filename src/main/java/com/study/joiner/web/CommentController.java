package com.study.joiner.web;

import com.study.joiner.config.auth.LoginUser;
import com.study.joiner.config.auth.dto.SessionUser;
import com.study.joiner.service.BoardService;
import com.study.joiner.service.CommentService;
import com.study.joiner.web.dto.BoardDto;
import com.study.joiner.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;

    // 댓글 등록
    @PostMapping("/detail/{id}/comment")
    public String createComment(@PathVariable Long id, @ModelAttribute CommentDto commentDto,
                                @LoginUser SessionUser user, Model model) {
        commentService.createComment(id, commentDto, user.getEmail());
        BoardDto boardDto = boardService.getOtherUserBoard(id);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }

    // 댓글 수정
    @PutMapping("/detail/{id}/comment/{c_id}")
    public String updateComment(@PathVariable Long id, @PathVariable Long c_id
            , @ModelAttribute CommentDto commentDto, Model model) {
        commentService.update(c_id, commentDto);
        BoardDto boardDto = boardService.getOtherUserBoard(id);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }

    // 댓글 삭제
    @DeleteMapping("/detail/{id}/comment/{c_id}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long c_id, Model model) {
        commentService.delete(c_id);
        BoardDto boardDto = boardService.getOtherUserBoard(id);
        model.addAttribute("board", boardDto);
        return "view/detail";
    }
}
