package com.hello.chat.global;

import java.util.Arrays;

public enum VisaType {
    D1,
    D2,
    D4,
    D5,
    D6,
    D7,
    D8,
    D9,
    D10,
    C4,
    E1,
    E2,
    E3,
    E4,
    E5,
    E6,
    E7,
    E8,
    E9,
    E10,
    F1,
    F2,
    F3,
    F5,
    F6;

    public static boolean checkTranslatorFormat(String visaType) {
        return Arrays.stream(VisaType.values())
                .noneMatch(visa -> visa.name().equals(visaType));
    }

    public static VisaType findVisaType(String visaType) {
        return Arrays.stream(VisaType.values())
                .filter(visa -> visa.name().equals(visaType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 visa type이 없습니다."));
    }


}
