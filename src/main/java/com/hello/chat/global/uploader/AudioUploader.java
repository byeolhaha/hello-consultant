package com.hello.chat.global.uploader;

import org.springframework.web.multipart.MultipartFile;

public interface AudioUploader {

    AudioUploadResponse upload(MultipartFile multipartFile);
}
