package com.project.capstonedesign.domain.EmailService.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailServiceDTO {
    @NotEmpty(message = "이메일을 입력해주세요")
    public String email;
}
