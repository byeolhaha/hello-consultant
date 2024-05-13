package com.hello.chat.repository.banword;

import com.hello.chat.domain.BanWord;
import com.hello.chat.global.SourceLanguage;

import java.util.List;

public interface BanWordRepository {

    List<BanWord> findBySourceLanguage(SourceLanguage sourceLanguage);

    BanWord save(BanWord banWord);

}
