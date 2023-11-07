package com.project.capstonedesign.domain.EmailService.Service;

import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import com.project.capstonedesign.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class FindPasswordService {

    private final UserRepository userRepository;
    private final UserService userService;

    public String FindPassword(String email)  {

        String userPassword = "isEmpty";

        if(userRepository.findByEmail(email).isPresent()){
            User user = userService.findByEmail(email);
            userPassword = user.getPassword();
        }

        return userPassword;
    }
}
