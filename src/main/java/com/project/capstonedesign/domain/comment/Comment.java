package com.project.capstonedesign.domain.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.thumbsUp.ThumbsUp;
import com.project.capstonedesign.domain.util.BaseTimeEntity;
import com.project.capstonedesign.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Comment parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> child = new ArrayList<>();


    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId")
    private Board board;

    @JsonIgnore
    @Column(name = "parentId", insertable = false, updatable = false)
    private Long parentId;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThumbsUp> thumbsUps = new ArrayList<>();

    @Builder
    public Comment(String content, Board board,Comment parent) {
        this.content = content;
        this.parent= parent;
        writtenBoard(board);
    }


    private void writtenBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

    public void writeUser(User user) {
        this.user = user;
    }

    public Long updateComment(String content) {
        this.content = content;
        return this.getCommentId();
    }

    public void updateParent(Comment comment) {
        this.parent = comment;
    }
}
