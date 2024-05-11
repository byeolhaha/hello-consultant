package com.hellomeritz.chat.service;

import com.hellomeritz.chat.repository.banword.BanWordStorage;
import org.ahocorasick.trie.Emit;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BanWordFilter {

    private final BanWordStorage banWordStorage;

    public BanWordFilter(BanWordStorage banWordStorage) {
        this.banWordStorage = banWordStorage;
    }

    public String validate(String sourceLanguage, String originText) {
        return banWordStorage.getBanWord(sourceLanguage).parseText(originText)
            .stream()
            .map(Emit::getKeyword)
            .collect(Collectors.joining(","));
    }

}
