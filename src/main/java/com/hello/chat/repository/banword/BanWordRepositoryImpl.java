package com.hello.chat.repository.banword;

import com.hello.chat.domain.BanWord;
import com.hello.chat.global.SourceLanguage;
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
