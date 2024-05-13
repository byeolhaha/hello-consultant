package com.hello.global;

import com.hello.member.controller.dto.request.ConsultantLoginRequest;
import com.hello.member.controller.dto.request.ConsultantSignUpRequest;
import com.hello.member.domain.FinancialConsultant;
import com.hello.member.service.dto.result.ConsultantLoginResult;
import com.hello.member.service.dto.result.ConsultantMatchResult;
import com.hello.member.service.dto.result.ConsultantSignUpResult;
import com.hello.member.service.dto.result.FcInfoResult;

public class FinancialConsultantFixture {

    private FinancialConsultantFixture() {
        throw new AssertionError("생성자를 통해서 인스턴스를 생성할 수 없습니다.");
    }

    public static FinancialConsultant financialConsultant() {
        return FinancialConsultant.of(
            "010-1234-6789",
            "박지호",
            "https://gcp//meritz-profile/mypicture",
            "안녕하세요 저는 박지호이고 자동차 보험 전문입니다."
        );
    }

    public static FinancialConsultant financialConsultantNotVailable() {
        FinancialConsultant financialConsultant = FinancialConsultant.of(
            "010-1234-6789",
            "박지호",
            "https://gcp//meritz-profile/mypicture",
            "안녕하세요 저는 박지호이고 자동차 보험 전문입니다."
        );
        financialConsultant.startConsulting();

        return financialConsultant;
    }

    public static FcInfoResult fcInfoResult() {
        return new FcInfoResult(
            1L,
            "010-1234-6789",
            "김별",
            "https://gcp//meritz-profile/mypicture",
            "안녕하세요 저는 박지호이고 자동차 보험 전문입니다."
        );
    }

    public static ConsultantMatchResult consultantMatchResult() {
        return new ConsultantMatchResult(1L);
    }

    public static ConsultantSignUpResult consultantSignUpResult() {
        return new ConsultantSignUpResult(1L);
    }

    public static ConsultantSignUpRequest consultantSignUpRequest() {
        return new ConsultantSignUpRequest(
            "123401343",
            "123456789TWE!@",
            "01012345678",
            "KIM BYEOL",
            "https://gcp//meritz-profile/mypicture",
            "hello, my name is byeol");
    }

    public static ConsultantLoginResult consultantLoginResult() {
        return new ConsultantLoginResult(true, 1L);
    }

    public static ConsultantLoginRequest consultantLoginRequest() {
        return new ConsultantLoginRequest(
            "123401343",
            "12345678TWE!@");
    }

}
