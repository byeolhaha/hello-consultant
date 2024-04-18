package com.hellomeritz.member.repository;

import com.hellomeritz.member.domain.Foreigner;

public interface ForeignRepository {

    Foreigner save(Foreigner foreigner);

    Long getUserId(String macAddress);

    Foreigner getById(Long userId);
}
