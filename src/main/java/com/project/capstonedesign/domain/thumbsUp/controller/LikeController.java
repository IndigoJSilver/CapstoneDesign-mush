package com.project.capstonedesign.domain.thumbsUp.controller;

import com.project.capstonedesign.domain.thumbsUp.dto.LikeDto;
import com.project.capstonedesign.domain.thumbsUp.service.LikeService;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public void insertLike(@RequestBody @Valid LikeDto likeDto) throws Exception {
        likeService.addLike(likeDto);
    }

    @DeleteMapping
    public void deleteLike(@RequestBody @Valid LikeDto likeDto) {
        likeService.deleteLike(likeDto);
    }

}
