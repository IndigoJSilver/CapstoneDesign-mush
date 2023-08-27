package com.project.capstonedesign.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {
    //private Long userId;
    private String nickname;
    private String email;
    private String password;
    private String name;
    private String cellphone;
}
