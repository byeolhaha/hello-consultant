package com.hellomeritz.chat.global.uploader;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AudioUploader {

    AudioUploadResponse upload(MultipartFile multipartFile);
}
