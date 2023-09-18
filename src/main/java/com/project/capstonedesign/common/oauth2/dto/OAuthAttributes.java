package com.project.capstonedesign.common.oauth2.dto;

import static com.project.capstonedesign.common.oauth2.service.OauthProviderConstant.*;

import java.util.Map;
import java.util.UUID;

import com.project.capstonedesign.common.jwt.util.PasswordUtil;
import com.project.capstonedesign.common.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.project.capstonedesign.common.oauth2.userinfo.OAuth2UserInfo;
import com.project.capstonedesign.domain.user.Role;
import com.project.capstonedesign.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class OAuthAttributes {
    private final String nameAttributeKey;
    private final OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(String oauthProvider, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if (!isKakao(oauthProvider)) {
            log.info("KaKao 아님");
            throw new IllegalArgumentException("지원하지 않는 Provider 입니다. : " + oauthProvider);
        }
        return ofKakao(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(String oauthProvider, OAuth2UserInfo oAuth2UserInfo) {
        return User.builder()
                .oauthProvider(oauthProvider)
                .oauthId(oAuth2UserInfo.getOauthId())
                .email(UUID.randomUUID() + "@kakao.com")
                .nickname(oAuth2UserInfo.getNickname())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .password(PasswordUtil.generateRandomPassword())
                .name("-")
                .cellphone("-")
                .role(Role.GUEST)
                .build();
    }
}