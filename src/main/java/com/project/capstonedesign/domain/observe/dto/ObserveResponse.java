package com.project.capstonedesign.domain.observe.dto;

import com.project.capstonedesign.domain.observe.Observe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ObserveResponse {

    private Long observeId;
    private String image;
    private double lng;
    private double lat;
    private LocalDateTime localDateTime;

    public static ObserveResponse of(Observe observe) {
        return new ObserveResponse(
                observe.getObserveId(),
                observe.getImage(),
                observe.getLng(),
                observe.getLat(),
                observe.getUpdatedDate()
        );
    }
}
