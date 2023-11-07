package com.project.capstonedesign.domain.SMSService.Controller;

import com.project.capstonedesign.domain.SMSService.Service.SMSUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class SMSController {

    private final SMSUtil smsUtil;

    @PostMapping("login/sendSMS")
    public String sendSMS(@RequestParam("cellphone") String cellphone) throws MessagingException, UnsupportedEncodingException {

        Random rand  = new Random();
        String authcode = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            authcode+=ran;
        }

        System.out.println("수신자 번호 : " + cellphone);
        System.out.println("인증번호 : " + authcode);
        smsUtil.sendOne(cellphone,authcode);
        return authcode;
    }
}
