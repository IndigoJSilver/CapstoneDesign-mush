package com.project.capstonedesign.domain.board.controller;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.Type;
import com.project.capstonedesign.domain.board.dto.BoardWriteDto;
import com.project.capstonedesign.domain.board.service.BoardService;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 전체 게시글 조회
    @GetMapping()
    public ApiResult<List<Board>> findAllBoard() {
        try {
            return ApiResult.success(boardService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 특정 게시글 조회
    @GetMapping("/{articleId}")
    public ApiResult<Board> findBoard(@PathVariable Long articleId) {
        try {
            return ApiResult.success(boardService.findById(articleId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시판 별 게시글 조회
    @GetMapping("/type/{type}")
    public ApiResult<List<Board>> findByType(@PathVariable Type type) {
        try {
            return ApiResult.success(boardService.findByType(type));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 회원이 작성한 모든 게시글 조회
    @GetMapping("/users/{userId}")
    public ApiResult<List<Board>> findByUserId(@PathVariable Long userId) {
        try {
            return ApiResult.success(boardService.findByUser(userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 수정
    @PutMapping("/edit/{userId}/{articleId}")
    public ApiResult<Long> updateBoard(@PathVariable Long userId, @PathVariable Long articleId, @RequestBody BoardWriteDto boardWriteDto) {
        try {
            return ApiResult.success(boardService.upadateBoard(userId, articleId, boardWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 작성
    @PostMapping("/post/{userId}")
    public ApiResult<Long> writeBoard(@PathVariable Long userId, @RequestBody BoardWriteDto boardWriteDto) {
        try {
            Long articleId = boardService.writeBoard(userId, boardWriteDto);
            return ApiResult.success(articleId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{userId}/{articleId}")
    public void deleteBoard(@PathVariable Long userId, @PathVariable Long articleId) {
        boardService.deleteBoard(userId, articleId);
    }
}
