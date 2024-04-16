package com.hellomeritz.chat.global.stt;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.retrying.TimedRetryAlgorithm;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.speech.v1.*;
import com.hellomeritz.chat.global.exception.ErrorCode;
import com.hellomeritz.chat.global.exception.custom.SttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.threeten.bp.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class SttGoogleManager implements SttManager {

    private static final String GOOGLE_BUCKET_PATH = "https://storage.googleapis.com/";
    private static final String GOOGLE_BUCKET_HOST = "gs://";

    @Value("${google.stt.credential.path}")
    private String credentialsPath;

    public SttResponse asyncRecognizeGcs(SttRequest request) {
        String gcsUri = request.audioUrl().replace(GOOGLE_BUCKET_PATH, GOOGLE_BUCKET_HOST);

        SpeechSettings.Builder speechSettingsBuilder = getCredentials();
        TimedRetryAlgorithm timedRetryAlgorithm = setTimeRetry();
        speechSettingsBuilder.longRunningRecognizeOperationSettings().setPollingAlgorithm(timedRetryAlgorithm);

        SpeechSettings speechSettings = null;
        try {
            speechSettings = speechSettingsBuilder.build();
        } catch (IOException e) {
            throw new SttException(ErrorCode.STT_SPEECH_ERROR);
        }

        // Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
        try (SpeechClient speech = SpeechClient.create(speechSettings)) {

            // Configure remote file request for FLAC
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.ENCODING_UNSPECIFIED)
                            .setLanguageCode(request.sourceLang())
                            .setSampleRateHertz(16000)
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

            // Use non-blocking call for getting file transcription
            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
                    speech.longRunningRecognizeAsync(config, audio);
            while (!response.isDone()) {
                Thread.sleep(100);
            }

            List<SpeechRecognitionResult> results = response.get().getResultsList();

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                return new SttResponse(alternative.getTranscript());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new SttException(ErrorCode.STT_IO_ERROR);
        } catch (ExecutionException e) {
            throw new SttException(ErrorCode.STT_EXECUTION_ERROR);
        }

        return SttResponse.emptySttResponse();
    }

    private SpeechSettings.Builder getCredentials() {
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
            return SpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build().toBuilder();
        } catch (IOException e) {
            throw new SttException(ErrorCode.STT_CREDENTIAL_ERROR);
        }
    }

    private TimedRetryAlgorithm setTimeRetry() {
        return OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                        .setInitialRetryDelay(Duration.ofMillis(500L))
                        .setRetryDelayMultiplier(1.5)
                        .setMaxRetryDelay(Duration.ofMillis(5000L))
                        .setInitialRpcTimeout(Duration.ZERO)
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ZERO)
                        .setTotalTimeout(Duration.ofHours(24L)) // set polling timeout to 24 hours
                        .build());
    }

}
