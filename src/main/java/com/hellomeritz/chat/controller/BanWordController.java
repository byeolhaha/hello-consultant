package com.hellomeritz.chat.controller;

import com.hellomeritz.chat.service.BanWordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/banwords")
public class BanWordController {

    private final BanWordService banWordService;

    public BanWordController(BanWordService banWordService) {
        this.banWordService = banWordService;
    }

    @PatchMapping
    public ResponseEntity<Void> uploadBanWord( ) {
        banWordService.uploadBanWordsToMemory();

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

}
