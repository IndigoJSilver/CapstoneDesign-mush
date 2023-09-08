package com.project.capstonedesign.domain.thumbsUp.service;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.exception.NotFoundBoardException;
import com.project.capstonedesign.domain.board.repository.BoardRepository;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.comment.exception.NotFoundCommentException;
import com.project.capstonedesign.domain.comment.repository.CommentRepository;
import com.project.capstonedesign.domain.thumbsUp.ThumbsUp;
import com.project.capstonedesign.domain.thumbsUp.dto.LikeDto;
import com.project.capstonedesign.domain.thumbsUp.exception.NotFoundLikeException;
import com.project.capstonedesign.domain.thumbsUp.repository.LikeRepository;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.exception.NotFoundUserException;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import com.project.capstonedesign.domain.util.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addLike(LikeDto likeDto) throws Exception {

        User user = userRepository.findById(likeDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException("userId 찾을 수 없음.: " + likeDto.getUserId()));

        Board board = boardRepository.findById(likeDto.getArticleId())
                .orElseThrow(() -> new NotFoundBoardException("articleId 찾을 수 없음.: " + likeDto.getArticleId()));

        Comment comment = commentRepository.findById(likeDto.getCommentId())
                .orElseThrow(() -> new NotFoundCommentException("commentId 찾을 수 없음.: " + likeDto.getCommentId()));

        if (likeRepository.findByUserAndBoard(user, board).isPresent()) {
            throw new DuplicateResourceException("이미 좋아요를 누른 게시물 입니다.: " + user.getUserId() + ", articleId: " + board.getArticleId());

        }

        if(likeRepository.findByUserAndComment(user, comment).isPresent()) {
            throw new DuplicateResourceException("이미 좋아요를 누른 댓글 입니다.: " + user.getUserId() + ", commentId: " + comment.getCommentId());
        }

        ThumbsUp thumbsUp = ThumbsUp.builder()
                .board(board)
                .comment(comment)
                .user(user)
                .build();

        likeRepository.save(thumbsUp);
    }

    @Transactional
    public void deleteLike(LikeDto likeDto) {

        User user = userRepository.findById(likeDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException("userId 찾을 수 없음.: " + likeDto.getUserId()));

        Board board = boardRepository.findById(likeDto.getArticleId())
                .orElseThrow(() -> new NotFoundBoardException("articleId 찾을 수 없음.: " + likeDto.getArticleId()));

        Comment comment = commentRepository.findById(likeDto.getCommentId())
                .orElseThrow(() -> new NotFoundCommentException("commentId 찾을 수 없음.: " + likeDto.getCommentId()));

        ThumbsUp thumbsUp = likeRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new NotFoundLikeException("likeId 찾을 수 없음.: "));

        likeRepository.delete(thumbsUp);
    }
}
