package com.hello.member.controller.dto.response;

import com.hello.member.service.dto.result.ForeignSaveIpAddressResult;

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
