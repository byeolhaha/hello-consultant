package com.hellomeritz.member.domain;

import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.global.VisaType;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Getter
@Entity
public class Foreigner {

    private static final Long MIN_USER_ID = 1L;
    private static final LocalDate MAX_BIRTH_DATE = LocalDate.now();
    private static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1800, 1, 1);

    @Column(name = "foreigner_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mac_address")
    private String macAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private SourceLanguage language;

    @Enumerated(EnumType.STRING)
    @Column(name = "visa_type")
    private VisaType visaType;

    @Column(name = "has_resident_card")
    private boolean hasResidentCard;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    protected Foreigner() {
    }

    private Foreigner(
            Long userId,
            String macAddress,
            SourceLanguage sourceLanguage,
            VisaType visaType,
            boolean hasResidentCard,
            LocalDate birthDate
    ) {
        Assert.isTrue(userId >= MIN_USER_ID, "user id는 양수여야 합니다.");
        Assert.hasLength(macAddress, "mac address는 빈값일 수 없습니다.");
        Assert.notNull(sourceLanguage, "외국인의 언어는 null일 수 없습니다.");
        Assert.notNull(visaType, "외국인의 visa type은 null일 수 없습니다.");
        Assert.isTrue(birthDate.isAfter(MAX_BIRTH_DATE),
                String.format("생일은 %s를 넘길 수 없습니다.", MAX_BIRTH_DATE));
        Assert.isTrue(birthDate.isBefore(MIN_BIRTH_DATE),
                String.format("생일은 %s보다 작을 수 없습니다.", MIN_BIRTH_DATE));

        this.id = userId;
        this.macAddress = macAddress;
        this.language = sourceLanguage;
        this.birthDate = birthDate;
        this.visaType = visaType;
        this.hasResidentCard = hasResidentCard;
    }

    public static Foreigner of(
            Long userId,
            String macAddress,
            SourceLanguage sourceLanguage,
            VisaType visaType,
            boolean hasResidentCard,
            LocalDate birthDate
    ) {
        return new Foreigner(
                userId,
                macAddress,
                sourceLanguage,
                visaType,
                hasResidentCard,
                birthDate
        );
    }

    public static Foreigner of(

    ) {
        return new Foreigner();
    }

}
