package com.hellomeritz.member.controller.dto;

import com.hellomeritz.member.service.dto.result.ForeignSaveIpAddressResult;

public record ForeignSaveIpAddressResponse(
        String ipAddress
) {
    public static ForeignSaveIpAddressResponse to(
            ForeignSaveIpAddressResult result) {
        return new ForeignSaveIpAddressResponse(
                result.ipAddress()
        );
    }
}
