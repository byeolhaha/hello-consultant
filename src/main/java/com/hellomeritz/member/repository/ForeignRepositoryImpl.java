package com.hellomeritz.member.repository;

import com.hellomeritz.member.domain.Foreigner;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public Foreigner getById(Long userId) {
        return foreignJpaRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 정보가 존재하지 않습니다."));
    }
}
