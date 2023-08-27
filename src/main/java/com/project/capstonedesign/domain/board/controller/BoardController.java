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
@RequestMapping("/boards")
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
    @GetMapping("/{boardId}")
    public ApiResult<Board> findBoard(@PathVariable Long boardId) {
        try {
            return ApiResult.success(boardService.findById(boardId));
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
    @PutMapping("/{userId}/{boardId}")
    public ApiResult<Long> updateBoard(@PathVariable Long userId, @PathVariable Long boardId, @RequestBody BoardWriteDto boardWriteDto) {
        try {
            return ApiResult.success(boardService.upadateBoard(userId, boardId, boardWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 작성
    @PostMapping("/{userId}")
    public ApiResult<Long> writeBoard(@PathVariable Long userId, @RequestBody BoardWriteDto boardWriteDto) {
        try {
            Long boardId = boardService.writeBoard(userId, boardWriteDto);
            return ApiResult.success(boardId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{userId}/{boardId}")
    public void deleteBoard(@PathVariable Long userId, @PathVariable Long boardId) {
        boardService.deleteBoard(userId, boardId);
    }
}
