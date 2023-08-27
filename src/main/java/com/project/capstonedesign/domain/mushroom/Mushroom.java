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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mushId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private Long rareItem;

    @Enumerated(EnumType.STRING)
    private WhichMush whichMush;

    @Column(nullable = false)
    private String fileUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "mushroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Observe> observes = new ArrayList<>();

    @Builder
    public Mushroom(String name, String info, Long rareItem, WhichMush whichMush, String fileUrl) {
        this.name = name;
        this.info = info;
        this.rareItem = rareItem;
        this.whichMush = whichMush;
        this.fileUrl = fileUrl;
    }

}
