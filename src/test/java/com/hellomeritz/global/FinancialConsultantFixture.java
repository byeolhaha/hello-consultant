package com.hellomeritz.global;

import com.hellomeritz.member.domain.FinancialConsultant;
import com.hellomeritz.member.service.dto.result.FcInfoResult;

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

    public static FcInfoResult fcInfoResult() {
        return new FcInfoResult(
            1L,
            "010-1234-6789",
            "김별",
            "https://gcp//meritz-profile/mypicture",
            "안녕하세요 저는 박지호이고 자동차 보험 전문입니다."
        );
    }
}
