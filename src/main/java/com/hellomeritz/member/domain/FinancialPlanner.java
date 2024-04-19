package com.hellomeritz.member.domain;

import jakarta.persistence.*;

@Entity
public class FinancialPlanner {

    @Column(name = "financial_planner_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financialPlannerId;

    private String phoneNumber;

    private String name;
    private String profileUrl;
    private String introduceMessage;
}
