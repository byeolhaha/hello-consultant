package com.hello.member.service;

import com.hello.global.FinancialConsultantFixture;
import com.hello.global.ForeignFixture;
import com.hello.member.domain.ConsultationState;
import com.hello.member.domain.FinancialConsultant;
import com.hello.member.domain.Foreigner;
import com.hello.member.repository.fc.FinancialConsultantRepository;
import com.hello.member.repository.foreign.ForeignRepository;
import com.hello.member.service.dto.param.FinancialConsultantChangeStateParam;
import com.hello.member.service.dto.param.FinancialConsultantInfoGetParam;
import com.hello.member.service.dto.param.ForeignerInfoGetParam;
import com.hello.member.service.dto.result.ConsultantMatchResult;
import com.hello.member.service.dto.result.FcInfoResult;
import com.hello.member.service.dto.result.ForeignerInfoResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ForeignRepository foreignRepository;

    @Autowired
    private FinancialConsultantRepository financialConsultantRepository;

    @DisplayName("외국인의 정보를 조회한다.")
    @Test
    void getForeignerInfo() {
        // given
        Foreigner foreigner = foreignRepository.save(ForeignFixture.foreigner());

        // when
        ForeignerInfoResult result = memberService.getForeignerInfo(new ForeignerInfoGetParam(foreigner.getForeignerId()));

        // then
        assertThat(result.birthDate()).isEqualTo(foreigner.getBirthDate().toString());
        assertThat(result.userId()).isEqualTo(foreigner.getForeignerId());
        assertThat(result.visaType()).isEqualTo(foreigner.getVisaType().name());
        assertThat(result.sourceLanguage()).isEqualTo(foreigner.getLanguage().name());
    }

    @DisplayName("설계사의 정보를 조회한다.")
    @Test
    void getFcInfo() {
        // given
        FinancialConsultant financialConsultant = financialConsultantRepository.save(FinancialConsultantFixture.financialConsultant());

        // when
        FcInfoResult result = memberService.getFinancialConsultantInfo(new FinancialConsultantInfoGetParam(financialConsultant.getFinancialConsultantId()));

        // then
        assertThat(result.financialConsultantId()).isEqualTo(financialConsultant.getFinancialConsultantId());
        assertThat(result.introduceMessage()).isEqualTo(financialConsultant.getIntroduceMessage());
        assertThat(result.name()).isEqualTo(financialConsultant.getName());
        assertThat(result.phoneNumber()).isEqualTo(financialConsultant.getPhoneNumber());
        assertThat(result.profileUrl()).isEqualTo(financialConsultant.getProfileUrl());
    }

    @DisplayName("현재 상담 가능한 설계사를 매칭하고 매칭하면 상태를 상담 불가능 상태로 변경한다.")
    @Test
    void matchConsultant() {
        // given
        FinancialConsultant financialConsultant = financialConsultantRepository.save(FinancialConsultantFixture.financialConsultant());

        // when
        ConsultantMatchResult consultantMatchResult = memberService.matchConsultant();

        // then
        assertThat(consultantMatchResult.fcId()).isEqualTo(financialConsultant.getFinancialConsultantId());
    }

    @DisplayName("현재 상담 가능한 설계사가 없는 경우 예외를 던진다.")
    @Test
    void matchConsultant_unavailable_throwException() {
        // given
        financialConsultantRepository.save(FinancialConsultantFixture.financialConsultantNotVailable());

        // when
        assertThrows(RuntimeException.class
            , ()->memberService.matchConsultant()
        );
    }

    @DisplayName("상담원은 상담이 끝나면 본인의 상태를 UNAVAILABLE로 바꿀 수 있다.")
    @Test
    void changeConsultationState() {
        // given
        FinancialConsultant financialConsultant = financialConsultantRepository.save(FinancialConsultantFixture.financialConsultantNotVailable());

        // when
        memberService.endConsultation(new FinancialConsultantChangeStateParam(financialConsultant.getFinancialConsultantId()));
        FinancialConsultant changedFinancialConsultant = financialConsultantRepository.getFinancialConsultant(financialConsultant.getFinancialConsultantId());

        // then
        assertThat(changedFinancialConsultant.getConsultationState()).isEqualTo(ConsultationState.AVAILABLE.name());
    }

}
