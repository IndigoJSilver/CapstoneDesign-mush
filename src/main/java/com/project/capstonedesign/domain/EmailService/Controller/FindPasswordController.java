package com.project.capstonedesign.domain.EmailService.Controller;

import com.project.capstonedesign.domain.EmailService.Service.FindPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class FindPasswordController {
    private final FindPasswordService findPasswordService;

    @PostMapping("login/findPassword")
    public String FindPassword(@RequestParam("email") String userEmail) throws MessagingException, UnsupportedEncodingException {

        String password = findPasswordService.FindPassword(userEmail);

        return password;
    }

}
