package com.project.capstonedesign.domain.SMSService.Service;

import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import com.project.capstonedesign.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindIDService {
    private final UserRepository userRepository;
    private final UserService userService;

    public String FindID(String cellphone) {

        String userEmail = "isEmpty";

        if(userRepository.findByCellphone(cellphone).isPresent()){
            User user = userService.findByCellphone(cellphone);
            userEmail = user.getEmail();
        }

        return userEmail;
    }

}
