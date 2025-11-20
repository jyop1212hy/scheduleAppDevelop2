package com.scheduleappdevelop2.comment.service;

import com.scheduleappdevelop2.comment.dto.CommentCreateRequest;
import com.scheduleappdevelop2.comment.dto.CommentResponse;
import com.scheduleappdevelop2.comment.entity.Comment;
import com.scheduleappdevelop2.comment.repository.CommentRepository;
import com.scheduleappdevelop2.global.exception.ScheduleNotFoundException;
import com.scheduleappdevelop2.global.exception.UnauthorizedUserAccessException;
import com.scheduleappdevelop2.global.exception.UserNotFoundException;
import com.scheduleappdevelop2.schedule.entity.Schedule;
import com.scheduleappdevelop2.schedule.repository.ScheduleRepository;
import com.scheduleappdevelop2.user.dto.sessionUser.SessionUser;
import com.scheduleappdevelop2.user.entity.User;
import com.scheduleappdevelop2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse createComment(Long scheduleId, CommentCreateRequest request, SessionUser sessionUser) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new UserNotFoundException(sessionUser.getId()));

        Comment comment = Comment.of(request.getContent(), schedule, user);
        Comment saved = commentRepository.save(comment);

        return CommentResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long scheduleId) {
        return commentRepository.findAllByScheduleId(scheduleId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentCreateRequest request, SessionUser sessionUser) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new UnauthorizedUserAccessException(sessionUser.getId());
        }

        comment.update(request.getContent());
        return CommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, SessionUser sessionUser) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(sessionUser.getId())) {
            throw new UnauthorizedUserAccessException(sessionUser.getId());
        }

        commentRepository.delete(comment);
    }
}
