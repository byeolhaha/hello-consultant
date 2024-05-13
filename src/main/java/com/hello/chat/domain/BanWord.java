package com.hello.chat.domain;

import com.hello.chat.global.SourceLanguage;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Entity
public class BanWord {

    @Column(name = "ban_word_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long banWordId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "source_language", nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceLanguage sourceLanguage;

    protected BanWord() {}

    private BanWord(
        String text,
        SourceLanguage sourceLanguage
    ) {
        Assert.hasLength(text, "text는 필수값입니다.");
        this.text = text;
        this.sourceLanguage = sourceLanguage;
    }

    public static BanWord to(
        String text,
        SourceLanguage sourceLanguage
    ) {
        return new BanWord(
            text,
            sourceLanguage
        );
    }

}
