package com.project.capstonedesign.domain.comment.repository;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoard(Board board);
    Optional<Comment> findByUserAndBoard(User user, Board board);
}
