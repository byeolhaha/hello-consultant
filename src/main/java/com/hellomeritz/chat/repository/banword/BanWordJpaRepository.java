package com.hellomeritz.chat.repository.banword;

import com.hellomeritz.chat.domain.BanWord;
import com.hellomeritz.chat.global.SourceLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BanWordJpaRepository extends JpaRepository<BanWord, Long> {

    List<BanWord> findBySourceLanguage(SourceLanguage sourceLanguage);
}
