package com.project.capstonedesign.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentWriteDto {

    private Long commentId;
    private Long parentId;
    private String content;

    public CommentWriteDto(String content) {
        this.content = content;
    }
}
