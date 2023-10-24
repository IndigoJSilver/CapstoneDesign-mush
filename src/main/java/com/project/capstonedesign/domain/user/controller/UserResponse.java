package com.project.capstonedesign.domain.user.controller;

import com.project.capstonedesign.domain.user.Role;
import com.project.capstonedesign.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse  {

    private Long userId;
    private String nickname;
    private String email;
    private String name;
    private String cellphone;
    private String oauthProvider;
    private String oauthId;
    private String refreshToken;
    private String imageUrl;
    private Role role;

    public static UserResponse of(User user) {
        return new UserResponse(user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getName(),
                user.getCellphone(),
                user.getOauthProvider(),
                user.getOauthId(),
                user.getRefreshToken(),
                user.getImageUrl(),
                user.getRole());
    }
}
