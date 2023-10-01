package com.project.capstonedesign.domain.observe.controller;

import com.project.capstonedesign.domain.observe.dto.ObserveResponse;
import com.project.capstonedesign.domain.observe.service.ObserveService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/observes")
@RequiredArgsConstructor
public class ObserveController {

    private final ObserveService observeService;

    @PostMapping("/save")
    public ApiResult<Long> saveObserve(@AuthenticationPrincipal User user, @RequestPart("data") ObserveResponse observeResponse, @RequestPart(value = "image") MultipartFile image) {
        try {
            Long observeId = observeService.saveMushroom(user.getUserId(), observeResponse, image);
            return ApiResult.success(observeId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

}
