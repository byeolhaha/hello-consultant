package com.hello.chat.service;

import com.hello.chat.domain.BanWord;
import com.hello.chat.global.SourceLanguage;
import com.hello.chat.repository.banword.BanWordRepository;
import com.hello.chat.repository.banword.BanWordStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanWordService {

    private final BanWordStorage banWordStorage;
    private final BanWordRepository banWordRepository;

    public BanWordService(BanWordStorage banWordStorage, BanWordRepository banWordRepository) {
        this.banWordStorage = banWordStorage;
        this.banWordRepository = banWordRepository;
    }

    public void uploadBanWordsToMemory() {
       for(SourceLanguage sourceLanguage : SourceLanguage.values()) {
           List<BanWord> banWords = banWordRepository.findBySourceLanguage(sourceLanguage);
           banWordStorage.upload(sourceLanguage.name(), banWords);
       }
    }

}
