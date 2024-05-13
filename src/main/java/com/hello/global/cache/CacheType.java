package com.hello.global.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    FOREIGNER_PROFILE("foreigner", 1, 10000),
    CONSULTANT_PROFILE("consultant", 3, 1000);

    private final String cacheName;
    private final int expiredHourAfterWrite;
    private final int maximumSize;

    CacheType(String cacheName, int expiredHourAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredHourAfterWrite = expiredHourAfterWrite;
        this.maximumSize = maximumSize;
    }
}
