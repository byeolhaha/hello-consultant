package com.hellomeritz.global;

import com.hellomeritz.member.controller.dto.ForeignInfoSaveRequest;
import com.hellomeritz.member.service.dto.result.ForeignCreateResult;
import com.hellomeritz.member.service.dto.result.ForeignInfoSaveResult;
import jakarta.validation.constraints.NotBlank;

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
                "en-US",
                "E1",
                true,
                "19970121"
        );
    }
}
