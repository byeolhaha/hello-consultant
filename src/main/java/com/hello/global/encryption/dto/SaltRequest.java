package com.hello.global.encryption.dto;

public record SaltRequest(
        String ipAddress,
        String chatRoomEnterDateTime
) {
    public String combineSaltResources() {
        return ipAddress + chatRoomEnterDateTime;
    }
}
