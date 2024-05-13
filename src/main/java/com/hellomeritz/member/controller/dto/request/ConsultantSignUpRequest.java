package com.hellomeritz.member.controller.dto.request;

import com.hellomeritz.global.encryption.PassWord;
import com.hellomeritz.member.service.dto.param.ConsultantSignUpParam;
import jakarta.validation.constraints.NotBlank;

public record ConsultantSignUpRequest(

    @NotBlank(message = "employNumber인 사원번호는 null이거나 빈값일 수 없습니다.")
    String employeeNumber,

    @NotBlank(message = "consultantPassword는 null이거나 빈값일 수 없습니다.")
    String consultantPassword,

    @NotBlank(message = "phoneNumber는 null이거나 빈값일 수 없습니다.")
    String phoneNumber,

    @NotBlank(message = "name호는 null이거나 빈값일 수 없습니다.")
    String name,

    @NotBlank(message = "profileUrl는 null이거나 빈값일 수 없습니다.")
    String profileUrl,

    @NotBlank(message = "introduceMessage는 null이거나 빈값일 수 없습니다.")
    String introduceMessage
) {
    public ConsultantSignUpParam toConsultantSignUpParam() {
        return new ConsultantSignUpParam(
            employeeNumber,
            PassWord.of(consultantPassword),
            phoneNumber,
            name,
            profileUrl,
            introduceMessage
        );
    }
}
