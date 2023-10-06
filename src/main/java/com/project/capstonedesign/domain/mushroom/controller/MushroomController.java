package com.project.capstonedesign.domain.mushroom.controller;

import com.project.capstonedesign.domain.mushroom.Mushroom;
import com.project.capstonedesign.domain.mushroom.dto.MushroomResponse;
import com.project.capstonedesign.domain.mushroom.service.MushroomService;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/mushrooms")
@RequiredArgsConstructor
public class MushroomController {

    private final MushroomService mushroomService;

    // 전체 버섯 목록 조회
    @GetMapping()
    public ApiResult<List<MushroomResponse>> findAllMushroom() {
        try {
            return ApiResult.success(mushroomService.findAll().stream().map(MushroomResponse::of).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 특정 버섯 조회
    @GetMapping("/{mushId}")
    public ApiResult<MushroomResponse> findMushroom(@PathVariable Long mushId) {
        try {
            return ApiResult.success(MushroomResponse.of(mushroomService.findById(mushId)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

}
