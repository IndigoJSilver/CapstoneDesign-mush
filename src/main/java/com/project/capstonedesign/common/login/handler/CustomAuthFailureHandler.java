package com.project.capstonedesign.common.login.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그인에 실패하였습니다. 이메일과 비밀번호를 확인해주세요.");
        log.info("로그인에 실패하였습니다. 메시지: {}", exception.getMessage());
    }
}
