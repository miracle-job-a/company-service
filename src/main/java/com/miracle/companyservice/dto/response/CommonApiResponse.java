package com.miracle.companyservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema (subTypes = {SuccessApiResponse.class, ErrorApiResponse.class})
public class CommonApiResponse {

    private final int httpStatus;
    private final String message;

    public CommonApiResponse(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

