package com.hellomeritz.chat.service;

import com.hellomeritz.chat.domain.BanWord;
import com.hellomeritz.chat.global.SourceLanguage;
import com.hellomeritz.chat.repository.banword.BanWordRepository;
import com.hellomeritz.chat.repository.banword.BanWordStorage;
import com.hellomeritz.global.BanWordFixture;
import org.ahocorasick.trie.Trie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class BanWordServiceTest {

    @Autowired
    private BanWordRepository banWordRepository;

    @Autowired
    private BanWordStorage banWordStorage;

    @Autowired
    private BanWordService banWordService;

    @DisplayName("DB에 저장된 금칙어에 대한 정보를 메모리에 잘 올리는지 확인한다.")
    @Test
    void uploadBanWordToMemory() {
        // given
        String sourceLanguage = SourceLanguage.CHINA.name();
        BanWord banWord = BanWordFixture.banWordByChina();
        banWordRepository.save(banWord);

        // when
        banWordService.uploadBanWordsToMemory();
        Trie savedBanWords = banWordStorage.getBanWord(sourceLanguage);

        // then
        assertThat(savedBanWords.containsMatch(banWord.getText())).isTrue();
    }

}