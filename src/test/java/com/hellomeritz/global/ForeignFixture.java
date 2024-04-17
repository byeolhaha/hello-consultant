package com.hellomeritz.global;

import com.hellomeritz.member.service.dto.result.ForeignCreateResult;

public class ForeignFixture {

    private ForeignFixture() {
        throw new AssertionError("생성자를 통해서 인스턴스를 생성할 수 없습니다.");
    }

    public static ForeignCreateResult foreignCreateResult() {
        return new ForeignCreateResult(1L);
    }
}
