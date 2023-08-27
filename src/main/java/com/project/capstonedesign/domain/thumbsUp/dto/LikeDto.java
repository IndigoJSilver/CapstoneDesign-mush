package com.project.capstonedesign.domain.thumbsUp.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeDto {

    private Long userId;
    private Long boardId;
    private Long commentId;

    public LikeDto(Long userId, Long boardId, Long commentId) {
        this.userId = userId;
        this.boardId = boardId;
        this.commentId = commentId;
    }
}
