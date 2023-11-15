package com.miracle.companyservice.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse {

    private final HttpStatus httpStatus;
    private final String message;

    public ApiResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

