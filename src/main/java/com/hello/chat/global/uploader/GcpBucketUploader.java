package com.hello.chat.global.uploader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class GcpBucketUploader implements AudioUploader {

    private static final String GOOGLE_BUCKET_API = "https://storage.googleapis.com/";

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Override
    public AudioUploadResponse upload(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();
        String type = multipartFile.getContentType();

        try {
            InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

            Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(type)
                .build();
            storage.createFrom(blobInfo, multipartFile.getInputStream());

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("gcp key는 해당 위치에 존재하지 않습니다.");
        } catch (IOException e) {
            throw new RuntimeException("파일을 변환하는데 문제가 발생했습니다.");
        }

        return AudioUploadResponse.to(makeAudioUrl(uuid));
    }

    private String makeAudioUrl(String uuid) {
        return GOOGLE_BUCKET_API + bucketName + "/" + uuid;
    }

}
