package com.project.capstonedesign.domain.EmailService.Controller;

import com.project.capstonedesign.domain.EmailService.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/emailConfirm")
    public String emailConfirm(@RequestParam("email") String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }

}
