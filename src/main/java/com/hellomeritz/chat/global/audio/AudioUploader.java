package com.hellomeritz.chat.global.audio;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AudioUploader {

    AudioUploadResponse upload(MultipartFile multipartFile) throws IOException;
}
