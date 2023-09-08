package com.project.capstonedesign.domain.thumbsUp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ThumbsUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId")
    private Board board;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @Builder
    public ThumbsUp(User user, Board board, Comment comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }

}
