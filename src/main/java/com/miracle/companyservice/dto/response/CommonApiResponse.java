package com.miracle.companyservice.dto.response;

import lombok.Getter;

@Getter
public class CommonApiResponse {

    private final int httpStatus;
    private final String message;

    public CommonApiResponse(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

