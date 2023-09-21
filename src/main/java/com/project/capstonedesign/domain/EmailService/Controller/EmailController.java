package com.project.capstonedesign.domain.EmailService.Controller;

import com.project.capstonedesign.domain.EmailService.DTO.EmailServiceDTO;
import com.project.capstonedesign.domain.EmailService.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final MailService emailService;

    @PostMapping("login/mailConfirm")
    public String mailConfirm(@RequestBody EmailServiceDTO emailDto) throws MessagingException, UnsupportedEncodingException {

        String authCode = emailService.sendEmail(emailDto.getEmail());
        return authCode;
    }
}
