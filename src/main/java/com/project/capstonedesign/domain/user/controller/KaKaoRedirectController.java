package com.project.capstonedesign.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KaKaoRedirectController {
    @GetMapping("/api/users/oauth2/login")
    public String test(@RequestParam("code")String code) {
        log.info("code : " + code);
        return "로그인성공";
    }
}
