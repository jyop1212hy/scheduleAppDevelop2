package com.scheduleappdevelop2.comment.controller;

import com.scheduleappdevelop2.comment.dto.CommentCreateRequest;
import com.scheduleappdevelop2.comment.dto.CommentResponse;
import com.scheduleappdevelop2.comment.service.CommentService;
import com.scheduleappdevelop2.global.exception.NotLoggedInException;
import com.scheduleappdevelop2.user.dto.sessionUser.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public CommentResponse create(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateRequest request,
            HttpServletRequest sessionRequest) {

        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new NotLoggedInException();

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new NotLoggedInException();

        return commentService.createComment(scheduleId, request, sessionUser);
    }

    // 댓글 전체 조회
    @GetMapping
    public List<CommentResponse> findAll(@PathVariable Long scheduleId) {
        return commentService.getComments(scheduleId);
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public CommentResponse update(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentCreateRequest request,
            HttpServletRequest sessionRequest) {

        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new NotLoggedInException();
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new NotLoggedInException();

        return commentService.updateComment(commentId, request, sessionUser);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public String delete(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            HttpServletRequest sessionRequest) {

        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new NotLoggedInException();
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new NotLoggedInException();

        commentService.deleteComment(commentId, sessionUser);
        return "댓글이 삭제되었습니다.";
    }
}
