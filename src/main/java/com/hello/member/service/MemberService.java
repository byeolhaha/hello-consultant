package com.hello.member.service;

import com.hello.member.domain.FinancialConsultant;
import com.hello.member.domain.Foreigner;
import com.hello.member.global.IpSensor;
import com.hello.global.encryption.PasswordEncoder;
import com.hello.global.encryption.dto.EncryptionResponse;
import com.hello.member.global.sms.SmsManager;
import com.hello.member.repository.fc.FinancialConsultantRepository;
import com.hello.member.repository.foreign.ForeignRepository;
import com.hello.member.service.dto.param.*;
import com.hello.member.service.dto.result.*;
import org.springframework.cache.annotation.Cacheable;
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

    private final PasswordEncoder passwordEncoder;

    public MemberService(ForeignRepository foreignRepository, FinancialConsultantRepository financialConsultantRepository, IpSensor ipSensor, SmsManager smsManager, PasswordEncoder passwordEncoder) {
        this.foreignRepository = foreignRepository;
        this.financialConsultantRepository = financialConsultantRepository;
        this.ipSensor = ipSensor;
        this.smsManager = smsManager;
        this.passwordEncoder = passwordEncoder;
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

    @Cacheable(cacheNames = "consultant", key = "#param.userId()")
    public FcInfoResult getFinancialConsultantInfo(FinancialConsultantInfoGetParam param) {
        FinancialConsultant financialConsultant
            = financialConsultantRepository.getFinancialConsultant(param.userId());
        return FcInfoResult.of(financialConsultant);
    }

    @Cacheable(cacheNames = "foreigner", key = "#param.userId()")
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
    public ConsultantSignUpResult signUp(ConsultantSignUpParam param) {
        String clientIp = ipSensor.getClientIP();
        EncryptionResponse encryptionResponse
            = passwordEncoder.encrypt(param.toEncryptionRequest(clientIp));

        FinancialConsultant savedFinancialConsultant
            = financialConsultantRepository.save(param.toFinancialConsultant(encryptionResponse));

        return ConsultantSignUpResult.to(savedFinancialConsultant.getFinancialConsultantId());
    }

    @Transactional
    public ConsultantLoginResult login(ConsultantLoginParam param) {
        FinancialConsultant financialConsultant
            = financialConsultantRepository.getByEmployeeNumber(param.employeeNumber());
        boolean isAuthorized = passwordEncoder.matchPassword(param.toPasswordMatchRequest(financialConsultant));

        if (isAuthorized) {
            createConsultantPassword(param, financialConsultant);
        }
        return ConsultantLoginResult.to(isAuthorized, financialConsultant.getFinancialConsultantId());
    }

    public void createConsultantPassword(ConsultantLoginParam param, FinancialConsultant financialConsultant) {
        String clientIp = ipSensor.getClientIP();
        EncryptionResponse encryptionResponse
            = passwordEncoder.encrypt(param.toEncryptionRequest(clientIp));

        financialConsultant.changePassword(encryptionResponse.password());
        financialConsultant.changeSalt(encryptionResponse.salt());
    }

    @Transactional
    public void endConsultation(FinancialConsultantChangeStateParam param) {
        FinancialConsultant financialConsultant = financialConsultantRepository.getFinancialConsultant(param.fcId());
        financialConsultant.endConsulting();
    }

}
