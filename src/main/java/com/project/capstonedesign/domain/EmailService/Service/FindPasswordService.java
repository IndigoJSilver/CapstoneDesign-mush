package com.project.capstonedesign.domain.EmailService.Service;

import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPasswordService {
    private final UserService userService;

    public  String FindPassword(String email) {

        User user = userService.findByEmail(email);

        String userPassword = user.getPassword();

        return userPassword;
    }


}
