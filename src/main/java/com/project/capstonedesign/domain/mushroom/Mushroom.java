package com.project.capstonedesign.domain.mushroom;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.capstonedesign.domain.observe.Observe;
import com.project.capstonedesign.domain.util.BaseTimeEntity;
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
public class Mushroom extends BaseTimeEntity {
    @Id
    @Column(name = "mushId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mushId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 4000)
    private String feature;

    @Column(nullable = false)
    private Long rarity;

    @Enumerated(EnumType.STRING)
    private WhichMush whichMush;

    @Column(nullable = false,length = 1000)
    private String image;

    @Column(nullable = false)
    private String isCatched;

    @JsonManagedReference
    @OneToMany(mappedBy = "mushroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Observe> observes = new ArrayList<>();

    @Builder
    public Mushroom(Long mushId,String name, String feature, Long rarity, WhichMush whichMush, String image, String isCatched) {
        this.mushId = mushId;
        this.name = name;
        this.feature = feature;
        this.rarity = rarity;
        this.whichMush = whichMush;
        this.image = image;
        this.isCatched = isCatched;
    }

    public void updateIsCatch() {
        this.isCatched = "true";
    }
}