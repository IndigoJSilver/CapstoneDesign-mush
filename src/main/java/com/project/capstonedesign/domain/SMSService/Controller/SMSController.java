package com.project.capstonedesign.domain.SMSService.Controller;

import com.project.capstonedesign.domain.SMSService.Service.SMSUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class SMSController {

    private final SMSUtil smsUtil;

    @PostMapping("/check/sendSMS")
    public String sendSMS(@RequestBody UserPhoneDto userPhoneDto) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        String phoneNumber = userPhoneDto.getPhoneNum();

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        smsUtil.sendOne(phoneNumber,numStr);
        return numStr;
    }

    @Data
    public static class UserPhoneDto {

        @NotEmpty(message = "휴대번호을 입력해주세요")
        public String phoneNum;
    }
}
