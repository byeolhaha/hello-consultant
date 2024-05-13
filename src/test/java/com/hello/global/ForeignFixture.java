package com.hello.global;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.VisaType;
import com.hello.member.controller.dto.request.ForeignInfoSaveRequest;
import com.hello.member.domain.BirthDate;
import com.hello.member.domain.Foreigner;
import com.hello.member.service.dto.param.ForeignerInfoGetParam;
import com.hello.member.service.dto.result.*;

public class ForeignFixture {

    private ForeignFixture() {
        throw new AssertionError("생성자를 통해서 인스턴스를 생성할 수 없습니다.");
    }

    public static ForeignCreateResult foreignCreateResult() {
        return new ForeignCreateResult(1L);
    }

    public static ForeignInfoSaveResult foreignInfoSaveResult() {
        return new ForeignInfoSaveResult(
            1L
        );
    }

    public static ForeignInfoSaveRequest foreignInfoSaveRequest() {
        return new ForeignInfoSaveRequest(
            "US",
            "E1",
            true,
            "19970121",
            "Jenie"
        );
    }

    public static ForeignSaveIpAddressResult foreignSaveIpAddressResult() {
        return new ForeignSaveIpAddressResult(
            "127.0.0.1"
        );
    }

    public static UserCheckIsFcResult userCheckIsFcResult() {
        return new UserCheckIsFcResult(true);
    }

    public static Foreigner foreigner() {
        return Foreigner.of(
            SourceLanguage.CHINA,
            VisaType.D1,
            false,
            BirthDate.of("19970121"),
            "Jenie"
        );
    }

    public static ForeignerInfoGetParam foreignerInfoGetParam(long foreignerId) {
        return new ForeignerInfoGetParam(foreignerId);
    }

    public static ForeignerInfoResult foreignerInfoResult() {
        return new ForeignerInfoResult(
            1L,
            "US",
            "E1",
            true,
            "1997-01-21",
            "Jenie"
        );
    }

}
