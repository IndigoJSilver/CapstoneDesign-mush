package com.project.capstonedesign.domain.comment.dto;

import com.project.capstonedesign.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long parentId;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.parentId = comment.getParentId();
    }
}
