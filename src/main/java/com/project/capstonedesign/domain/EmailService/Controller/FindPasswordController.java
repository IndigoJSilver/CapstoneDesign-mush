package com.project.capstonedesign.domain.EmailService.Controller;

import com.project.capstonedesign.domain.EmailService.Service.FindPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindPasswordController {
    private final FindPasswordService findPasswordService;

    @PostMapping("login/FindPassword")
    public String mailConfirm(@RequestParam("email") String userEmail) {

        String password = findPasswordService.FindPassword(userEmail);

        return password;
    }

}
