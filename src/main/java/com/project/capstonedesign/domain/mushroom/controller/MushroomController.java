package com.project.capstonedesign.domain.mushroom.controller;

import com.project.capstonedesign.domain.mushroom.Mushroom;
import com.project.capstonedesign.domain.mushroom.dto.MushroomResponse;
import com.project.capstonedesign.domain.mushroom.service.MushroomService;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mushrooms")
@RequiredArgsConstructor
public class MushroomController {

    private final MushroomService mushroomService;

    // 전체 버섯 목록 조회
    @GetMapping()
    public ApiResult<List<Mushroom>> findAllMushroom() {
        try {
            return ApiResult.success(mushroomService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 특정 버섯 조회
    @GetMapping("/{mushId}")
    public ApiResult<Mushroom> findMushroom(@PathVariable Long mushId, @RequestBody MushroomResponse mushroomResponse) {
        try {
            return ApiResult.success(mushroomService.findById(mushId, mushroomResponse));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

}
