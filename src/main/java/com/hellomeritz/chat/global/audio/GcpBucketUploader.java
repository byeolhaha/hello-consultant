package com.hellomeritz.chat.global.audio;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public AudioUploadResponse upload(MultipartFile multipartFile) throws IOException {
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        String uuid = UUID.randomUUID().toString();
        String type = multipartFile.getContentType();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(type)
                .build();

        storage.createFrom(blobInfo, multipartFile.getInputStream());

        return AudioUploadResponse.to(makeAudioUrl(uuid));
    }

    private String makeAudioUrl(String uuid) {
        return GOOGLE_BUCKET_API + bucketName + "/" + uuid;
    }
}
