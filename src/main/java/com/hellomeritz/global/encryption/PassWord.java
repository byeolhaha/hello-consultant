package com.hellomeritz.global.encryption;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class PassWord {

    private static final int MIN_PASSWORD_LENGTH = 10;
    private String password;

    private PassWord() {
    }

    private PassWord(
            String password
    ) {
        this.password = password;
    }

    public static PassWord of(
            String password
    ) {
        Assert.hasLength(password, "비밀번호는 빈값이나 null 일 수 없습니다.");
        checkLength(password);
        isContainEngAndSpecialCharAndDigit(password);

        return new PassWord(password);
    }

    private static void checkLength(String password) {
        int passwordLength = password.length();
        if (passwordLength < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(String.format("password의 자릿수가 %d를 넘지 않습니다. " +
                    "현재 자릿수는 %d 입니다.", MIN_PASSWORD_LENGTH, passwordLength));
        }
    }

    private static void isContainEngAndSpecialCharAndDigit(String password) {
        boolean hasEnglish = false;
        boolean hasSpecialCharacter = false;
        boolean hasDigit = false;

        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) {
                hasEnglish = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        if (!hasEnglish) {
            throw new IllegalArgumentException("비밀번호에 영어가 포함되어 있어야 합니다.");
        }
        if (!hasSpecialCharacter) {
            throw new IllegalArgumentException("비밀번호에 특수문자가 포함되어 있어야 합니다.");
        }
        if (!hasDigit) {
            throw new IllegalArgumentException("비밀번호에 숫자가 포함되어 있어야 합니다.");
        }
    }

}
