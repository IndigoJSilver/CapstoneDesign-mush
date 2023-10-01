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
    @Column(name = "observeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long observeId;

    @Column(nullable = false)
    private Double lng; // 경도

    @Column(nullable = false)
    private Double lat; // 위도

    @Column(nullable = false)
    private String image;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mushId")
    private Mushroom mushroom;

    @Builder
    public Observe(Double lng, Double lat, String image) {
        this.lng = lng;
        this.lat = lat;
        this.image= image;
    }
}
