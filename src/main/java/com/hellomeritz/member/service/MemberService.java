package com.hellomeritz.member.service;

import com.hellomeritz.member.domain.FinancialConsultant;
import com.hellomeritz.member.domain.Foreigner;
import com.hellomeritz.member.global.IpSensor;
import com.hellomeritz.member.global.sms.SmsManager;
import com.hellomeritz.member.repository.fc.FinancialConsultantRepository;
import com.hellomeritz.member.repository.foreign.ForeignRepository;
import com.hellomeritz.member.service.dto.param.*;
import com.hellomeritz.member.service.dto.result.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class MemberService {

    private static final Random random = new Random();

    private final ForeignRepository foreignRepository;
    private final FinancialConsultantRepository financialConsultantRepository;
    private final IpSensor ipSensor;
    private final SmsManager smsManager;

    public MemberService(ForeignRepository foreignRepository, FinancialConsultantRepository financialConsultantRepository, IpSensor ipSensor, SmsManager smsManager) {
        this.foreignRepository = foreignRepository;
        this.financialConsultantRepository = financialConsultantRepository;
        this.ipSensor = ipSensor;
        this.smsManager = smsManager;
    }

    @Transactional
    public ForeignInfoSaveResult saveForeignInfo(ForeignInfoSaveParam param) {
        Foreigner foreigner = foreignRepository.save(param.toForeigner());

        return ForeignInfoSaveResult.to(foreigner.getForeignerId());
    }

    @Transactional
    public ForeignCreateResult createForeignMember() {
        Foreigner foreigner = foreignRepository.save(Foreigner.of());

        return ForeignCreateResult.of(foreigner.getForeignerId());
    }

    public UserCheckIsFcResult checkUserIsFc(UserCheckIsFcParam param) {
        String clientIP = ipSensor.getClientIP();
        return UserCheckIsFcResult.to(foreignRepository.isFc(clientIP, param.userId()));
    }

    @Transactional
    public void notifyForeignerArrival(AlarmToFcParam param) {
        FinancialConsultant financialConsultant = financialConsultantRepository.getFinancialConsultant(param.fcId());
        smsManager.sendAlarmMessage(param.t0SmsSendRequest(financialConsultant.getPhoneNumber()));
    }

    public FcInfoResult getFinancialConsultantInfo(FinancialConsultantInfoGetParam param) {
        FinancialConsultant financialConsultant
            = financialConsultantRepository.getFinancialConsultant(param.userId());
        return FcInfoResult.of(financialConsultant);
    }

    public ForeignerInfoResult getForeignerInfo(ForeignerInfoGetParam param) {
        Foreigner foreigner = foreignRepository.getById(param.userId());
        return ForeignerInfoResult.of(foreigner);
    }

    @Transactional
    public ConsultantMatchResult matchConsultant() {
        List<FinancialConsultant> financialConsultants = financialConsultantRepository.getFinancialConsultantWithAvailable();
        FinancialConsultant financialConsultant = selectConsultant(financialConsultants);

        financialConsultant.startConsulting();

        return ConsultantMatchResult.to(financialConsultant.getFinancialConsultantId());
    }

    private FinancialConsultant selectConsultant(List<FinancialConsultant> consultants) {
        int randomIndex = random.nextInt(consultants.size());
        return consultants.get(randomIndex);
    }

    @Transactional
    public void endConsultation(FinancialConsultantChangeStateParam param) {
        FinancialConsultant financialConsultant = financialConsultantRepository.getFinancialConsultant(param.fcId());
        financialConsultant.endConsulting();
    }

}
