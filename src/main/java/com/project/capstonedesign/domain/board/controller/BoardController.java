package com.project.capstonedesign.domain.board.controller;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.Sort;
import com.project.capstonedesign.domain.board.Type;
import com.project.capstonedesign.domain.board.dto.BoardResponse;
import com.project.capstonedesign.domain.board.dto.BoardWriteDto;
import com.project.capstonedesign.domain.board.service.BoardService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/users")
    public ApiResult<List<Board>> findByUserId(@AuthenticationPrincipal User user) {
        try {
            return ApiResult.success(boardService.findByUser(user.getUserId()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 수정
    @PatchMapping("/edit/{articleId}")
    public ApiResult<Long> updateBoard(@AuthenticationPrincipal User user, @PathVariable Long articleId, @RequestBody BoardWriteDto boardWriteDto) {
        try {
            return ApiResult.success(boardService.upadateBoard(user.getUserId(), articleId, boardWriteDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 작성
    @PostMapping("/post")
    public ApiResult<Long> writeBoard(@AuthenticationPrincipal User user, @RequestPart("data") BoardWriteDto boardWriteDto,
                                      @RequestPart(value = "image") MultipartFile image) {
        try {
            Long articleId = boardService.writeBoard(user.getUserId(), boardWriteDto, image);
            return ApiResult.success(articleId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{articleId}")
    public void deleteBoard(@AuthenticationPrincipal User user, @PathVariable Long articleId) {
        boardService.deleteBoard(user.getUserId(), articleId);
    }

    // 게시글 키워드 조회
    @GetMapping("/search/{keyword}")
    public ApiResult<List<BoardResponse>> searchBoard(@PathVariable String keyword) {
        try {
            List<BoardResponse> result = boardService.searchBoard(keyword)
                    .stream()
                    .map(BoardResponse::of)
                    .collect(Collectors.toList());
            return ApiResult.success(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 게시글 정렬(좋아요 수, 작성 날짜 순)
    @GetMapping("/sort/{sort}/{limit}")
    public ApiResult<List<BoardResponse>> sortBoard(@PathVariable String sort, @PathVariable int limit) {
        try {
            List<BoardResponse> result = boardService.sortBoard(Sort.searchSortType(sort), limit)
                    .stream()
                    .map(BoardResponse::of)
                    .collect(Collectors.toList());
            return ApiResult.success(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }
}
