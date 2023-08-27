package com.project.capstonedesign.domain.comment.controller;

import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.comment.dto.CommentResponse;
import com.project.capstonedesign.domain.comment.dto.CommentWriteDto;
import com.project.capstonedesign.domain.comment.service.CommentService;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/{boardId}")
    public ApiResult<List<CommentResponse>> findComment(@PathVariable Long boardId) {
        try {
            return ApiResult.success(commentService.findAllCommentsInBoard(boardId).stream().map(CommentResponse::new).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 댓글 등록
    @PostMapping("/{userId}/{boardId}")
    public ApiResult<Long> writeComment(@PathVariable Long userId, @PathVariable Long boardId, @RequestBody CommentWriteDto commentWriteDto) {
        try {
            return ApiResult.success(commentService.writeComment(userId, boardId, commentWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 댓글 수정
    @PutMapping("/{userId}/{boardId}")
    public ApiResult<Long> updateComment(@PathVariable Long userId, @PathVariable Long boardId, @RequestBody CommentWriteDto commentWriteDto) {
        try {
            return ApiResult.success(commentService.updateComment(userId, boardId, commentWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{userId}/{boardId}")
    public void deleteComment(@PathVariable Long userId, @PathVariable Long boardId) {
        commentService.deleteComment(userId, boardId);
    }

    // 답글 등록
    @PostMapping("/{pardentId}/{boardId}")
    public ApiResult<Long> writeReply(@PathVariable Long pardentId, @PathVariable Long boardId, @RequestBody CommentWriteDto commentWriteDto) {
        try {
            return ApiResult.success(commentService.writeComment(pardentId, boardId, commentWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 답글 수정
    @PutMapping("/{parentId}/{boardId}")
    public ApiResult<Long> updateReply(@PathVariable Long parentId, @PathVariable Long boardId, @RequestBody CommentWriteDto commentWriteDto) {
        try {
            return ApiResult.success(commentService.updateComment(parentId, boardId, commentWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 답글 삭제
    @DeleteMapping("/{parentId}/{boardId}")
    public void deleteReply(@PathVariable Long parentId, @PathVariable Long boardId) {
        commentService.deleteComment(parentId, boardId);
    }


}
