package com.project.capstonedesign.domain.comment.service;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.repository.BoardRepository;
import com.project.capstonedesign.domain.board.service.BoardService;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.comment.dto.CommentWriteDto;
import com.project.capstonedesign.domain.comment.exception.NotFoundCommentException;
import com.project.capstonedesign.domain.comment.repository.CommentRepository;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import com.project.capstonedesign.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardService boardService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsInBoard(Long articleId) {
        Board board = boardService.findById(articleId);
        return commentRepository.findByBoard(board);
    }

    @Transactional(readOnly = true)
    public Comment findCommentByUserAndBoard(Long userId, Long articleId, Long commentId) {
        User user = userService.findById(userId);
        Board board = boardService.findById(articleId);
        return commentRepository.findByUserAndBoardAndCommentId(user, board, commentId)
                .orElseThrow(() -> new NotFoundCommentException(String.format("댓글 없음")));
    }

    // 댓글 작성
    @Transactional
    public Long writeComment(Long userId, Long articleId, CommentWriteDto commentWriteDto) {

        Board board = boardService.findById(articleId);
        User user = userService.findById(userId);

        Comment comment = Comment.builder()
                .content(commentWriteDto.getContent())
                .board(board)
                .build();


        if (commentWriteDto.getParentId() != null) {
            Comment parent = commentRepository.findById(commentWriteDto.getParentId())
                    .orElseThrow(() -> new NotFoundCommentException("commentId 없음 " + commentWriteDto.getParentId()));
            if (!parent.getBoard().getArticleId().equals(articleId) ) {
                throw new NotFoundCommentException("해당 게시물에 댓글이 없습니다.");
            }
            comment.updateParent(parent);
        }

        Comment saveComment = commentRepository.save(comment);
        user.writeComment(saveComment);
        return saveComment.getCommentId();
    }

    // 댓글 수정
    @Transactional
    public Long updateComment(Long userId, Long articleId, CommentWriteDto commentWriteDto) {
        Comment comment = findCommentByUserAndBoard(userId, articleId, commentWriteDto.getCommentId());
        return comment.updateComment(commentWriteDto.getContent());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long userId, Long articleId, Long commentId) {
        Comment comment = findCommentByUserAndBoard(userId, articleId, commentId);
        commentRepository.deleteById(comment.getCommentId());
    }

}
