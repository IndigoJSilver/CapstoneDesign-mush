package com.project.capstonedesign.domain.SMSService.Controller;

import com.project.capstonedesign.domain.SMSService.Service.FindIDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class FindIDController {
    private final FindIDService findIDService;

    @PostMapping("login/findId")
    public String FindPassword(@RequestParam("cellphone") String cellphone) throws MessagingException, UnsupportedEncodingException {

        String userEmail = findIDService.FindID(cellphone);

        return userEmail;
    }
}
