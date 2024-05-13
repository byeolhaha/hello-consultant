package com.hello.chat.repository.banword;

import com.hello.chat.domain.BanWord;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BanWordStorage {

    private static final Map<String, Trie> banWordBundle = new ConcurrentHashMap<>();

    public void upload(String sourceLanguage, List<BanWord> banWords) {
        banWordBundle.put(sourceLanguage, Trie.builder()
            .ignoreOverlaps()
            .addKeywords(banWords.stream().map(BanWord::getText).toList())
            .build());
    }

    public Trie getBanWord(String sourceLanguage) {
        return banWordBundle.get(sourceLanguage);
    }

}
