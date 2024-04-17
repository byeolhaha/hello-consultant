package com.hellomeritz.member.service;

import com.hellomeritz.chat.global.MacAddressGenerator;
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

    private final MacAddressGenerator macAddressGenerator;

    private final ForeignRepository foreignRepository;

    public MemberService(MacAddressGenerator macAddressGenerator, ForeignRepository foreignRepository) {
        this.macAddressGenerator = macAddressGenerator;
        this.foreignRepository = foreignRepository;
    }

    @Transactional
    public ForeignInfoSaveResult saveForeignInfo(ForeignInfoSaveParam param) {
        String macAddress = macAddressGenerator.get();
        Foreigner foreigner = foreignRepository.save(param.toForeigner(macAddress));

        return ForeignInfoSaveResult.to(
                foreigner.getId(),
                macAddress
        );
    }

    public ForeignUserIdResult getForeignUserId() {
        String macAddress = macAddressGenerator.get();
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
