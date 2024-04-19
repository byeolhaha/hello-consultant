package com.hellomeritz.member.service;

import com.hellomeritz.member.domain.Foreigner;
import com.hellomeritz.member.global.IpSensor;
import com.hellomeritz.member.repository.ForeignRepository;
import com.hellomeritz.member.service.dto.param.ForeignInfoSaveParam;
import com.hellomeritz.member.service.dto.param.ForeignSaveIpAddressParam;
import com.hellomeritz.member.service.dto.param.UserCheckIsFcParam;
import com.hellomeritz.member.service.dto.result.ForeignCreateResult;
import com.hellomeritz.member.service.dto.result.ForeignInfoSaveResult;
import com.hellomeritz.member.service.dto.result.ForeignSaveIpAddressResult;
import com.hellomeritz.member.service.dto.result.UserCheckIsFcResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final ForeignRepository foreignRepository;
    private final IpSensor ipSensor;

    public MemberService(ForeignRepository foreignRepository, IpSensor ipSensor) {
        this.foreignRepository = foreignRepository;
        this.ipSensor = ipSensor;
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

    @Transactional
    public ForeignSaveIpAddressResult saveForeignIpAddress(ForeignSaveIpAddressParam param) {
        Foreigner foreigner = foreignRepository.getById(param.userId());
        String clientIp = ipSensor.getClientIP();

        foreigner.updateIpAddress(clientIp);

        return ForeignSaveIpAddressResult.to(clientIp);
    }

    public UserCheckIsFcResult checkUserIsFc(UserCheckIsFcParam param) {
        String clientIP = ipSensor.getClientIP();
        return UserCheckIsFcResult.to(foreignRepository.isFc(clientIP, param.userId()));
    }

}
