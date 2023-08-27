package com.project.capstonedesign.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.capstonedesign.domain.board.Board;
import com.project.capstonedesign.domain.comment.Comment;
import com.project.capstonedesign.domain.observe.Observe;
import com.project.capstonedesign.domain.report.Report;
import com.project.capstonedesign.domain.thumbsUp.ThumbsUp;
import com.project.capstonedesign.domain.util.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cellphone;

//    @Enumerated(EnumType.STRING)
//    private Status status;

    @Column
    private String oauthProvider; // kakao

    @Column
    private String oauthId;

    @Column
    private String refreshToken;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThumbsUp> thumbsUps = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Observe> observes = new ArrayList<>();

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public User updateUser(String name, String password, String nickname, String cellphone, String imageUrl) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.cellphone = cellphone;
        this.imageUrl = imageUrl;
        return this;
    }

    public void writeBoard(Board board) {
        this.boards.add(board);
        board.createdByUser(this);
    }

    public void writeComment(Comment comment) {
        this.comments.add(comment);
        comment.writeUser(this);
    }

}
