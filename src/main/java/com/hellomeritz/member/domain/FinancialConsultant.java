package com.hellomeritz.member.domain;

import jakarta.persistence.*;

@Entity
public class FinancialPlanner {

    @Column(name = "financial_planner_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financialPlannerId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "introduce_message")
    private String introduceMessage;
}
