package com.project.capstonedesign.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdatdDto {
    private String name;
    private String password;
    private String nickname;
    private String cellphone;
    private String imageUrl;
}
