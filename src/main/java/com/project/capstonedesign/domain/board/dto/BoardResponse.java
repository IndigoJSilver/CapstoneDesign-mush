package com.project.capstonedesign.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.board.Type;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.thumbsUp.ThumbsUp;
import com.project.capstonedesign.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class BoardResponse {
    private Long articleId;

    private Type type;

    private String title;

    private String content;

    private String status;

    private String image;

    private Long userId;

    private String nickname;

    private LocalDateTime LocalDateTime;

    private int likeCount;

    public static BoardResponse of(Board board) {
        return new BoardResponse(board.getArticleId(),
                board.getType(),
                board.getTitle(),
                board.getContent(),
                board.getStatus(),
                board.getImage(),
                board.getUser().getUserId(),
                board.getUser().getNickname(),
                board.getUpdatedDate(),
                board.getThumbsUps().size());
    }

}
