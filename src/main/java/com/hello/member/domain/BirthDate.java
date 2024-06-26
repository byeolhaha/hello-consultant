package com.hello.member.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Getter
@Embeddable
public class BirthDate {

    private static String BITH_DATE_FORMAT = "yyyyMMdd";
    private static final LocalDate MAX_BIRTH_DATE = LocalDate.now();
    private static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1800, 1, 1);

    private LocalDate birthDate;

    protected BirthDate() {
    }

    private BirthDate(String birthDate) {
        LocalDate birthDateToTypo = parseBirthDate(birthDate);
        Assert.isTrue(birthDateToTypo.isBefore(MAX_BIRTH_DATE),
                String.format("%s 생일은 %s를 넘길 수 없습니다.", birthDate, MAX_BIRTH_DATE));
        Assert.isTrue(birthDateToTypo.isAfter(MIN_BIRTH_DATE),
                String.format("%s 생일은 %s보다 작을 수 없습니다.", birthDate, MIN_BIRTH_DATE));

        this.birthDate = birthDateToTypo;
    }

    public static BirthDate of(String birthDate) {
        return new BirthDate(birthDate);
    }

    public LocalDate parseBirthDate(String birthDate) {
        try {
            return LocalDate.parse(birthDate, DateTimeFormatter.ofPattern(BITH_DATE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 날짜 형식이 아닙니다.", e);
        }
    }

}
