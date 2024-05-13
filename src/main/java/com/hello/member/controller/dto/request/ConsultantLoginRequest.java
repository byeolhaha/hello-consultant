package com.hello.member.controller.dto.request;

import com.hello.member.service.dto.param.ConsultantLoginParam;
import jakarta.validation.constraints.NotBlank;

public record ConsultantLoginRequest(

    @NotBlank(message = "consultantPassword는 null이거나 빈값일 수 없습니다.")
    String consultantPassword,

    @NotBlank(message = "employNumber인 사원번호는 null이거나 빈값일 수 없습니다.")
    String employeeNumber
) {
    public ConsultantLoginParam toConsultantLoginParam() {
        return new ConsultantLoginParam(
            employeeNumber,
            consultantPassword
        );
    }
}
