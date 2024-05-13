package com.hello;

import com.hello.chat.service.BanWordService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BanWordApplicationRunner implements ApplicationRunner {

    private final BanWordService banWordService;

    public BanWordApplicationRunner(BanWordService banWordService) {
        this.banWordService = banWordService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        banWordService.uploadBanWordsToMemory();
    }

}
