package com.miracle.companyservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorApiResponse extends ApiResponse {

    private final String code;
    private final String exception;

    @Builder
    public ErrorApiResponse(HttpStatus httpStatus, String message, String code, String exception) {
        super(httpStatus, message);
        this.code = code;
        this.exception = exception;
    }
}

