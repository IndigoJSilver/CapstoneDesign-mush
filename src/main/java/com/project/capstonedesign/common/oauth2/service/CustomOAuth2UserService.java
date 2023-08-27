package com.project.capstonedesign.common.oauth2.service;

import static com.project.capstonedesign.common.oauth2.service.OauthProviderConstant.*;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.capstonedesign.common.oauth2.CustomOAuth2User;
import com.project.capstonedesign.common.oauth2.dto.OAuthAttributes;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String oauthProvider = getOauthProvider(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(oauthProvider, userNameAttributeName, attributes);

        User createdUser = getUser(extractAttributes, oauthProvider);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
    }

    private String getOauthProvider(String registrationId) {
        if (isKakao(registrationId)) {
            log.info("등록된 id가 일치하지 않습니다.");
            return KAKAO;
        }
        throw new IllegalArgumentException("지원하지 않는 Provider 입니다. : " + registrationId);
    }

    private User getUser(OAuthAttributes attributes, String oauthProvider) {
        User finduser = userRepository.findByOauthProviderAndOauthId(oauthProvider, attributes.getOAuth2UserInfo().getOauthId())
                .orElse(null);

        if (finduser == null) {
            return saveUser(attributes, oauthProvider);
        }
        return finduser;
    }

    private User saveUser(OAuthAttributes attributes, String oauthProvider) {
        User createdUser = attributes.toEntity(oauthProvider, attributes.getOAuth2UserInfo());
        return userRepository.save(createdUser);
    }

}