package com.project.capstonedesign.domain.mushroom.dto;

import com.project.capstonedesign.domain.mushroom.Mushroom;
import com.project.capstonedesign.domain.mushroom.WhichMush;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MushroomResponse {

    //private Long mushId;
    private String name;
    private String feature;
    private Long rarity;
    private WhichMush whichMush;
    private String image;
    private int picNum;
    private String isCatched;

    private static MushroomResponse of(Mushroom mushroom) {
        return new MushroomResponse(
                //mushroom.getMushId(),
                mushroom.getName(),
                mushroom.getFeature(),
                mushroom.getRarity(),
                mushroom.getWhichMush(),
                mushroom.getImage(),
                mushroom.getPicNum(),
                mushroom.getIsCatched()
        );
    }
}
