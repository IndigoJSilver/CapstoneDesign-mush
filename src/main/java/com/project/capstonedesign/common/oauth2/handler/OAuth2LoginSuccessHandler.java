package com.project.capstonedesign.common.oauth2.handler;

import com.project.capstonedesign.common.oauth2.CustomOAuth2User;
import com.project.capstonedesign.common.jwt.service.JwtService;
import com.project.capstonedesign.domain.user.Role;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 login 성공");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if (oAuth2User.getRole() == Role.GUEST) {
                String email =oAuth2User.getEmail();
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
            } else {
                loginSuccess(response, oAuth2User);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer" + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer" + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
