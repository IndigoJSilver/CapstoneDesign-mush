package com.project.capstonedesign.domain.board.repository;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.Type;
import com.project.capstonedesign.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByType(Type type);
    List<Board> findByUser(User user);

}
