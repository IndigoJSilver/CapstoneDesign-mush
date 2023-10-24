package com.project.capstonedesign.domain.thumbsUp.controller;

import com.project.capstonedesign.domain.thumbsUp.dto.LikeDto;
import com.project.capstonedesign.domain.thumbsUp.service.LikeService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/insert")
    public void insertLike(@RequestBody @Valid LikeDto likeDto, @AuthenticationPrincipal User user) throws Exception {
        likeService.addLike(likeDto, user);
    }

    @DeleteMapping("/delete")
    public void deleteLike(@RequestBody @Valid LikeDto likeDto, @AuthenticationPrincipal User user) {
        likeService.deleteLike(likeDto, user);
    }

}
