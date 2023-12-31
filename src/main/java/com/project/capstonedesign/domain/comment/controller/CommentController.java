package com.project.capstonedesign.domain.comment.controller;

import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.comment.dto.CommentResponse;
import com.project.capstonedesign.domain.comment.dto.CommentWriteDto;
import com.project.capstonedesign.domain.comment.service.CommentService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/{articleId}")
    public ApiResult<List<CommentResponse>> findComment(@PathVariable Long articleId) {
        try {
            return ApiResult.success(commentService.findAllCommentsInBoard(articleId).stream().map(CommentResponse::new).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 댓글 등록
    @PostMapping("/post/{articleId}")
    public ApiResult<Long> writeComment(@AuthenticationPrincipal User user, @PathVariable Long articleId, @RequestBody CommentWriteDto commentWriteDto) {
        try {
            return ApiResult.success(commentService.writeComment(user.getUserId(), articleId, commentWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 댓글 수정
    @PatchMapping("/edit/{articleId}")
    public ApiResult<Long> updateComment(@AuthenticationPrincipal User user, @PathVariable Long articleId, @RequestBody CommentWriteDto commentWriteDto) {
        try {
            return ApiResult.success(commentService.updateComment(user.getUserId(), articleId, commentWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{articleId}/{commentId}")
    public  ApiResult<String> deleteComment(@AuthenticationPrincipal User user, @PathVariable Long articleId, @PathVariable Long commentId) {
        commentService.deleteComment(user.getUserId(), articleId, commentId);
        return ApiResult.success("삭제");
    }
}
