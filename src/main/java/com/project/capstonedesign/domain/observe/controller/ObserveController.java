package com.project.capstonedesign.domain.observe.controller;

import com.project.capstonedesign.domain.observe.dto.ObserveResponse;
import com.project.capstonedesign.domain.observe.service.ObserveService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/observes")
@RequiredArgsConstructor
public class ObserveController {

    private final ObserveService observeService;

    // 발견한 버섯 사진 정보 저장
    @PostMapping("/save")
    public ApiResult<Long> saveObserve(@AuthenticationPrincipal User user, @RequestPart("data") ObserveRequest observeRequest, @RequestPart(value = "image") MultipartFile image) {
        try {
            Long observeId = observeService.saveMushroom(user.getUserId(), observeRequest, image);
            return ApiResult.success(observeId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

}
