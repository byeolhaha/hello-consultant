package com.hellomeritz.member.service;

import com.hellomeritz.member.global.IpSensor;
import com.hellomeritz.member.domain.Foreigner;
import com.hellomeritz.member.repository.ForeignRepository;
import com.hellomeritz.member.service.dto.param.ForeignInfoSaveParam;
import com.hellomeritz.member.service.dto.result.ForeignCreateResult;
import com.hellomeritz.member.service.dto.result.ForeignInfoSaveResult;
import com.hellomeritz.member.service.dto.result.ForeignUserIdResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final IpSensor ipSensor;

    private final ForeignRepository foreignRepository;

    public MemberService(IpSensor ipSensor, ForeignRepository foreignRepository) {
        this.ipSensor = ipSensor;
        this.foreignRepository = foreignRepository;
    }

    @Transactional
    public ForeignInfoSaveResult saveForeignInfo(ForeignInfoSaveParam param) {
        Foreigner foreigner = foreignRepository.save(param.toForeigner());

        return ForeignInfoSaveResult.to(foreigner.getId());
    }

    public ForeignUserIdResult getForeignUserId() {
        String macAddress = ipSensor.getClientIP();
        Long userId = foreignRepository.getUserId(macAddress);

        return ForeignUserIdResult.to(
                foreignRepository.getUserId(macAddress));
    }

    @Transactional
    public ForeignCreateResult createForeignMember() {
        Foreigner foreigner = foreignRepository.save(Foreigner.of());

        return ForeignCreateResult.of(foreigner.getId());
    }

}
