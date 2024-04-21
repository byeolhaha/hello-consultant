package com.hellomeritz.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

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

    protected FinancialConsultant() {
    }

    private FinancialConsultant(
        String phoneNumber,
        String name,
        String profileUrl,
        String introduceMessage
    ) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.profileUrl = profileUrl;
        this.introduceMessage = introduceMessage;
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

}
