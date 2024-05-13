package com.hello.member.repository.foreign;

import com.hello.member.domain.Foreigner;

public interface ForeignRepository {

    Foreigner save(Foreigner foreigner);

    boolean isFc(String ipAddress, Long userId);

    Foreigner getById(Long userId);
}
