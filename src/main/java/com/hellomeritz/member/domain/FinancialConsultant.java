package com.hellomeritz.member.domain;

import com.hellomeritz.global.encryption.PassWord;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

@Table(name = "financial_consultant")
@Getter
@Entity
public class FinancialConsultant {

    @Column(name = "financial_consultant_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financialConsultantId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "introduce_message")
    private String introduceMessage;

    @Column(name = "consultation_state")
    private String consultationState;

    @Column(name = "consultant_password")
    private String consultantPassword;

    @Column(name = "salt")
    private String salt;

    @Column(name = "employee_number")
    private String employeeNumber;

    protected FinancialConsultant() {
    }

    private FinancialConsultant(
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
    ) {
        Assert.hasLength(phoneNumber, "phoneNumber는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(name, "name는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(profileUrl, "profileUrl는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(introduceMessage, "introduceMessage는 null이거나 빈값일 수 없습니다.");

        this.phoneNumber = phoneNumber;
        this.name = name;
        this.profileUrl = profileUrl;
        this.introduceMessage = introduceMessage;
        this.consultationState = ConsultationState.AVAILABLE.name();
    }

    public static FinancialConsultant of(
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
    ) {
        return new FinancialConsultant(
            phoneNumber,
            name,
            profileUrl,
            introduceMessage
        );
    }

    private FinancialConsultant(
        String employeeNumber,
        String consultantPassword,
        String salt,
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
    ) {
        Assert.hasLength(employeeNumber, "employeeNumber는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(consultantPassword, "consultantPassword는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(salt, "salt는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(phoneNumber, "phoneNumber는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(name, "name는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(profileUrl, "profileUrl는 null이거나 빈값일 수 없습니다.");
        Assert.hasLength(introduceMessage, "introduceMessage는 null이거나 빈값일 수 없습니다.");

        this.employeeNumber = employeeNumber;
        this.consultantPassword = consultantPassword;
        this.salt = salt;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.profileUrl = profileUrl;
        this.introduceMessage = introduceMessage;
        this.consultationState = ConsultationState.AVAILABLE.name();
    }

    public static FinancialConsultant of(
        String employeeNumber,
        String consultantPassword,
        String salt,
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
    ) {
        return new FinancialConsultant(
            employeeNumber,
            consultantPassword,
            salt,
            phoneNumber,
            name,
            profileUrl,
            introduceMessage
        );
    }

    public void startConsulting() {
        this.consultationState = ConsultationState.UNAVAILABLE.name();
    }

    public void endConsulting() {
        this.consultationState = ConsultationState.AVAILABLE.name();
    }

    public void changePassword(String consultantPassword) {
        this.consultantPassword = consultantPassword;
    }

    public void changeSalt(String salt) {
        this.salt = salt;
    }
}
