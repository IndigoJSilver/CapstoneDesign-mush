package com.project.capstonedesign.domain.board.service;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.Type;
import com.project.capstonedesign.domain.board.dto.BoardWriteDto;
import com.project.capstonedesign.domain.board.exception.NotFoundBoardException;
import com.project.capstonedesign.domain.board.exception.NotHavePermissionBoardException;
import com.project.capstonedesign.domain.board.repository.BoardRepository;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;


    @Transactional(readOnly = true)
    public Board findById(Long articleId) {
        return boardRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundBoardException(String.format("Board is not found.")));
    }

    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Board> findByType(Type type) {
        return boardRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public List<Board> findByUser(Long userId) {
        User user = userService.findById(userId);
        return boardRepository.findByUser(user);
    }

    @Transactional
    public Long writeBoard(Long userId, BoardWriteDto boardWriteDto) {
        User user = userService.findById(userId);
        Board board = Board.builder()
                .type(boardWriteDto.getType())
                .title(boardWriteDto.getTitle())
                .content(boardWriteDto.getContent())
                .image("")
                .status("")
                .build();
        Board saveBoard = boardRepository.save(board);
        user.writeBoard(saveBoard);
        return saveBoard.getArticleId();
    }

    @Transactional
    public Long upadateBoard(Long userId, Long articleId, BoardWriteDto boardWriteDto) {
        User user = userService.findById(userId);
        Board board = findById(articleId);
        checkBoardLoginUser(user, board);
        Long updatedArticleId = board.updateBoard(
                boardWriteDto.getType(),
                boardWriteDto.getTitle(),
                boardWriteDto.getContent()
        );
        return updatedArticleId;
    }

    private void checkBoardLoginUser(User user, Board board) {
        if (!Objects.equals(board.getUser().getUserId(), user.getUserId())) {
            throw new NotHavePermissionBoardException("해당 게시물을 수정/삭제할 권한이 없습니다.");
        }
    }

    @Transactional
    public void deleteBoard(Long userId, Long articleId) {
        User user = userService.findById(userId);
        Board board = findById(articleId);
        checkBoardLoginUser(user, board);
        boardRepository.deleteById(articleId);
    }
}
