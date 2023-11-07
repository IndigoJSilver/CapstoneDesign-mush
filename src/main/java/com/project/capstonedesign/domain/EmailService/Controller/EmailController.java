package com.project.capstonedesign.domain.EmailService.Controller;

import com.project.capstonedesign.domain.EmailService.Service.EmailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.NotEmpty;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("login/sendEmail")
    public String mailConfirm(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {

        String authCode = emailService.sendEmail(email);
        return authCode;
    }
}
