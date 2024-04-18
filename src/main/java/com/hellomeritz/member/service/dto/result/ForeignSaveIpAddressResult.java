package com.hellomeritz.member.service.dto.result;

public record ForeignSaveIpAddressResult(
        String ipAddress
) {
    public static ForeignSaveIpAddressResult to(String ipAddress) {
        return new ForeignSaveIpAddressResult(ipAddress);
    }
}
