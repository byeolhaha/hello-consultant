package com.hellomeritz.member.global.encryption.dto;

public record EncryptionResponse(
        String password,
        String salt
) {
    public static EncryptionResponse to(String password, String salt) {
        return new EncryptionResponse(password, salt);
    }
}
