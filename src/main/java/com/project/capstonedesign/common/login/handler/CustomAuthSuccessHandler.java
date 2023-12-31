package com.project.capstonedesign.common.login.handler;

import com.project.capstonedesign.common.jwt.service.JwtService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String email = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        Optional<User> userDetails = userRepository.findByEmail(email);

        userDetails.ifPresent(user -> {
            user.updateRefreshToken(refreshToken);
            userRepository.saveAndFlush(user);
        });

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "로그인에 성공하였습니다.");
        jsonResponse.put("accessToken", accessToken);
        jsonResponse.put("refreshToken", refreshToken);

        if (userDetails.isPresent()) {
            User user = userDetails.get();
            jsonResponse.put("userId", user.getUserId());
            jsonResponse.put("image", user.getImageUrl());
            jsonResponse.put("nickname", user.getNickname());
            jsonResponse.put("email", user.getEmail());
            jsonResponse.put("cellphone", user.getCellphone());
        }

        try {
            response.getWriter().write(jsonResponse.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("로그인에 성공하였습니다. 이메일: {}", email);
        log.info("로그인에 성공하였습니다. AccessToken: {}", accessToken);
        log.info("로그인에 성공하였습니다. RefreshToken: {}", refreshToken);
        log.info("발급된 AccessToken 만료 기간: {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
