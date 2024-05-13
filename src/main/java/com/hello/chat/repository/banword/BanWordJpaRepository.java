package com.hello.chat.repository.banword;

import com.hello.chat.domain.BanWord;
import com.hello.chat.global.SourceLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BanWordJpaRepository extends JpaRepository<BanWord, Long> {

    List<BanWord> findBySourceLanguage(SourceLanguage sourceLanguage);
}
