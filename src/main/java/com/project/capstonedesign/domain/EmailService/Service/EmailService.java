package com.project.capstonedesign.domain.EmailService.Service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
}
