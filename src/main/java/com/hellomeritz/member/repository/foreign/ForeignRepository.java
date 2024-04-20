package com.hellomeritz.member.repository.foreign;

import com.hellomeritz.member.domain.Foreigner;
import org.springframework.data.repository.query.Param;

public interface ForeignRepository {

    Foreigner save(Foreigner foreigner);

    boolean isFc(String ipAddress, Long userId);

    Foreigner getById(Long userId);
}
