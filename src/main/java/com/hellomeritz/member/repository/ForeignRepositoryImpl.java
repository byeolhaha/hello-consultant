package com.hellomeritz.member.repository;

import com.hellomeritz.member.domain.Foreigner;
import org.springframework.stereotype.Repository;

@Repository
public class ForeignRepositoryImpl implements ForeignRepository {

    private final ForeignJpaRepository foreignJpaRepository;

    public ForeignRepositoryImpl(ForeignJpaRepository foreignJpaRepository) {
        this.foreignJpaRepository = foreignJpaRepository;
    }

    @Override
    public Foreigner save(Foreigner foreigner) {
        return foreignJpaRepository.save(foreigner);
    }

    @Override
    public Long getUserId(String macAddress) {
        return foreignJpaRepository.getUserId(macAddress);
    }
}
