package com.project.capstonedesign.domain.observe.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObserveRequest {

    private Long mushId;
    private double lng;
    private double lat;
}
