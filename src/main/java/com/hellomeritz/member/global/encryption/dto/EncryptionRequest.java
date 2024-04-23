package com.hellomeritz.member.global.encryption.dto;

import java.time.LocalDateTime;

public record EncryptionRequest(
        String password,
        String ipAddress,
        LocalDateTime chatRoomEnterDateTime
) {
    public SaltRequest toSaltRequest() {
        return new SaltRequest(
                ipAddress,
                chatRoomEnterDateTime.toString()
        );
    }

    public char[] getPasswordToCharArray() {
        return password.toCharArray();
    }
}
