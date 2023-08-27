package com.project.capstonedesign.common.oauth2.service;

public final class OauthProviderConstant {
    public static final String KAKAO = "kakao";
    public static boolean isKakao(String oauthProvider) {
        return oauthProvider.equals(KAKAO);
    }
}