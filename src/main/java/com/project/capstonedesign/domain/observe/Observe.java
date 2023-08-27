package com.project.capstonedesign.domain.observe;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.capstonedesign.domain.mushroom.Mushroom;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Observe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long observeId;

    @Column(nullable = false)
    private String locate;

    @Column(nullable = false)
    private String fileUrl;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mushId")
    private Mushroom mushroom;

    @Builder
    public void Observe(String locate, String fileUrl) {
        this.locate = locate;
        this.fileUrl = fileUrl;
    }
}
