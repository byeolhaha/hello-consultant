package com.hellomeritz.chat.repository.banword;

import com.hellomeritz.chat.domain.BanWord;
import com.hellomeritz.chat.global.SourceLanguage;

import java.util.List;

public interface BanWordRepository {

    List<BanWord> findBySourceLanguage(SourceLanguage sourceLanguage);

    BanWord save(BanWord banWord);

}
