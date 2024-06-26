package com.hello.member.repository.foreign;

import com.hello.member.domain.Foreigner;
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
    public boolean isFc(String ipAddress, Long userId) {
        return foreignJpaRepository.isFc(ipAddress, userId);
    }

    @Override
    public Foreigner getById(Long userId) {
        return foreignJpaRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 정보가 존재하지 않습니다."));
    }
}
