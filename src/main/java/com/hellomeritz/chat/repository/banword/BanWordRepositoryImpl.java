package com.hellomeritz.chat.repository.banword;

import com.hellomeritz.chat.domain.BanWord;
import com.hellomeritz.chat.global.SourceLanguage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BanWordRepositoryImpl implements BanWordRepository{

    private final BanWordJpaRepository banWordJpaRepository;

    public BanWordRepositoryImpl(BanWordJpaRepository banWordJpaRepository) {
        this.banWordJpaRepository = banWordJpaRepository;
    }

    public List<BanWord> findBySourceLanguage(SourceLanguage sourceLanguage) {
        return banWordJpaRepository.findBySourceLanguage(sourceLanguage);
    }

    @Override
    public BanWord save(BanWord banWord) {
        return banWordJpaRepository.save(banWord);
    }
}
