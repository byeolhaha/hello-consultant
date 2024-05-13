package com.hello.member.domain;

import com.hello.chat.global.SourceLanguage;
import com.hello.chat.global.VisaType;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Table(name = "foreigner")
@Getter
@Entity
public class Foreigner {

    private static final Long MIN_USER_ID = 1L;
    private static final LocalDate MAX_BIRTH_DATE = LocalDate.now();
    private static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1800, 1, 1);

    @Column(name = "foreigner_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foreignerId;

    @Column(name = "mac_address")
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private SourceLanguage language;

    @Enumerated(EnumType.STRING)
    @Column(name = "visa_type")
    private VisaType visaType;

    @Column(name = "has_resident_card")
    private boolean hasResidentCard;

    @Column(name = "birth_date")
    @Embedded
    private BirthDate birthDate;

    @Column(name = "name")
    private String name;

    protected Foreigner() {
    }

    private Foreigner(
            Long userId,
            SourceLanguage sourceLanguage,
            VisaType visaType,
            boolean hasResidentCard,
            BirthDate birthDate
    ) {
        Assert.isTrue(userId >= MIN_USER_ID, "user id는 양수여야 합니다.");
        Assert.notNull(sourceLanguage, "외국인의 언어는 null일 수 없습니다.");
        Assert.notNull(visaType, "외국인의 visa type은 null일 수 없습니다.");

        this.foreignerId = userId;
        this.language = sourceLanguage;
        this.birthDate = birthDate;
        this.visaType = visaType;
        this.hasResidentCard = hasResidentCard;
    }

    public static Foreigner of(
            Long userId,
            SourceLanguage sourceLanguage,
            VisaType visaType,
            boolean hasResidentCard,
            BirthDate birthDate
    ) {
        return new Foreigner(
                userId,
                sourceLanguage,
                visaType,
                hasResidentCard,
                birthDate
        );
    }

    private Foreigner(
        SourceLanguage sourceLanguage,
        VisaType visaType,
        boolean hasResidentCard,
        BirthDate birthDate,
        String name
    ) {
        Assert.notNull(sourceLanguage, "외국인의 언어는 null일 수 없습니다.");
        Assert.notNull(visaType, "외국인의 visa type은 null일 수 없습니다.");
        Assert.hasLength(name, "name은 null이거나 빈 값일 수 없습니다.");

        this.language = sourceLanguage;
        this.birthDate = birthDate;
        this.visaType = visaType;
        this.hasResidentCard = hasResidentCard;
        this.name = name;
    }

    public static Foreigner of(
        SourceLanguage sourceLanguage,
        VisaType visaType,
        boolean hasResidentCard,
        BirthDate birthDate,
        String name
    ) {
        return new Foreigner(
            sourceLanguage,
            visaType,
            hasResidentCard,
            birthDate,
            name
        );
    }

    public static Foreigner of() {
        return new Foreigner();
    }

    public void updateIpAddress(String ipAddress) {
        Assert.hasLength(ipAddress, "ipAddress는 null이거나 빈값일 수 없습니다.");
        this.ipAddress = ipAddress;
    }

    public LocalDate getBirthDate() {
        return birthDate.getBirthDate();
    }

}
