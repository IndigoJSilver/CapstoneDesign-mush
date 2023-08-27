package com.project.capstonedesign.domain.thumbsUp.repository;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.thumbsUp.ThumbsUp;
import com.project.capstonedesign.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<ThumbsUp, Long> {

    Optional<ThumbsUp> findByUserAndBoard(User user, Board board);
    Optional<ThumbsUp> findByUserAndComment(User user, Comment comment);
}
