package com.miracle.companyservice.dto.api;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseApi {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final String exception;

    @Builder
    public BaseApi(HttpStatus httpStatus, String code, String message, String exception) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.exception = exception;
    }

    public BaseApi(BaseApi baseApi) {
        this.httpStatus = baseApi.getHttpStatus();
        this.code = baseApi.getCode();
        this.message = baseApi.getMessage();
        this.exception = baseApi.getException();
    }
}
