package com.hellomeritz.chat.global.translator.google;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.hellomeritz.global.exception.ErrorCode;
import com.hellomeritz.global.exception.custom.TranslateException;
import com.hellomeritz.chat.global.translator.TranslationRequest;
import com.hellomeritz.chat.global.translator.TranslationResponse;
import com.hellomeritz.chat.global.translator.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class GoogleTranslator implements Translator {

    @Value("${google.credential.path}")
    private String credentialsPath;

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        Translate translate = TranslateOptions.newBuilder().setCredentials(credentials()).build().getService();

        Translation translation =
            translate.translate(
                request.contents(),
                Translate.TranslateOption.sourceLanguage(request.sourceLang().getGoogleTranslateLang()),
                Translate.TranslateOption.targetLanguage(request.targetLang().getGoogleTranslateLang()));

        return TranslationResponse.to(translation.getTranslatedText());
    }

    private GoogleCredentials credentials() {
        GoogleCredentials credentials = null;

        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
        } catch (IOException e) {
            throw new TranslateException(ErrorCode.TRANSLATE_CREDENTIAL_ERROR);
        }

        return credentials;
    }
}
