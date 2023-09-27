package com.project.capstonedesign.domain.board.repository;

import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.Type;
import com.project.capstonedesign.domain.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByType(Type type);
    List<Board> findByUser(User user);
    @Query("SELECT p " +
            "FROM Board p " +
            "LEFT JOIN FETCH p.thumbsUps l " +
            "LEFT JOIN FETCH l.user u " +
            "where p.title like %?1%")
    List<Board> findByTitleContaining(String keyword);

    @Query("SELECT p " +
            "FROM Board p " +
            "LEFT JOIN FETCH p.thumbsUps l " +
            "LEFT JOIN FETCH l.user u " +
            "ORDER BY p.updatedDate DESC ")
    List<Board> findSortByUpdate(PageRequest pageable);

    @Query("SELECT p " +
            "FROM Board p " +
            "LEFT JOIN FETCH p.thumbsUps l " +
            "LEFT JOIN FETCH l.user u " +
            "ORDER BY p.thumbsUps.size DESC ")
    List<Board> findSortByLikeCount(PageRequest pageable);


}

