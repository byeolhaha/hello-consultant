package com.hellomeritz.chat.global.stt;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.retrying.TimedRetryAlgorithm;
import com.google.cloud.speech.v1.*;
import com.hellomeritz.chat.global.uploader.AudioUploadResponse;
import org.threeten.bp.Duration;

import java.sql.SQLOutput;
import java.util.List;

public class SttGoogleManager {

    private SttGoogleManager() {
        throw new AssertionError("해당 클래스는 생성자로 인스턴스를 생성할 수 없습니다.");
    }

    public static String asyncRecognizeGcs(AudioUploadResponse audioUploadResponse, String sourceLang) {
        String gcsUri = audioUploadResponse.audioUrl().replace("https://storage.googleapis.com/", "gs://");

        // Configure polling algorithm
        SpeechSettings.Builder speechSettings = SpeechSettings.newBuilder();
        TimedRetryAlgorithm timedRetryAlgorithm =
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(500L))
                    .setRetryDelayMultiplier(1.5)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO) // ignored
                    .setRpcTimeoutMultiplier(1.0) // ignored
                    .setMaxRpcTimeout(Duration.ZERO) // ignored
                    .setTotalTimeout(Duration.ofHours(24L)) // set polling timeout to 24 hours
                    .build());
        speechSettings.longRunningRecognizeOperationSettings().setPollingAlgorithm(timedRetryAlgorithm);

        // Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
        try (SpeechClient speech = SpeechClient.create(speechSettings.build())) {

            // Configure remote file request for FLAC
            RecognitionConfig config =
                RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.ENCODING_UNSPECIFIED)
                    .setLanguageCode(sourceLang)
                    .setSampleRateHertz(16000)
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

            // Use non-blocking call for getting file transcription
            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
                speech.longRunningRecognizeAsync(config, audio);
            while (!response.isDone()) {
                Thread.sleep(10000);
            }

            List<SpeechRecognitionResult> results = response.get().getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                return alternative.getTranscript();
            }
        } catch (Exception e) {

        }
        return "";
    }

}
