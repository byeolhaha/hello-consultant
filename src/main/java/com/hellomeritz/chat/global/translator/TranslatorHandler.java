package com.hellomeritz.chat.global.translator;

import com.hellomeritz.chat.global.translator.deepl.DeeplTranslator;
import com.hellomeritz.chat.global.translator.google.GoogleTranslator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TranslatorHandler {

    private final Map<String, Translator> translators = new ConcurrentHashMap<>();
    private final DeeplTranslator deeplTranslator;
    private final GoogleTranslator googleTranslator;

    public TranslatorHandler(DeeplTranslator deeplTranslator, GoogleTranslator googleTranslator){
        this.deeplTranslator = deeplTranslator;
        this.googleTranslator = googleTranslator;
        translators.put(TranslateProvider.DEEPL.name(), deeplTranslator);
        translators.put(TranslateProvider.GOOGLE.name(), googleTranslator);
    }

    public Translator getTranslator(String translatorName) {
        return translators.get(translatorName);
    }
}
