package com.project.capstonedesign.domain.thumbsUp.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeDto {

    private Long articleId;
    private Long commentId;

    public LikeDto(Long articleId, Long commentId) {
        this.articleId = articleId;
        this.commentId = commentId;
    }
}
