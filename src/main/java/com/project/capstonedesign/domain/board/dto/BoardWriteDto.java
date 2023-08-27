package com.project.capstonedesign.domain.board.dto;

import com.project.capstonedesign.domain.board.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardWriteDto {

    private String title;
    private String content;
    private Type type;
}
