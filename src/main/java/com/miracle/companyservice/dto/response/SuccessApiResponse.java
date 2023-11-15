package com.miracle.companyservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessApiResponse<T> extends ApiResponse {

    private final T data;

    @Builder
    public SuccessApiResponse(HttpStatus httpStatus, String message, T data) {
        super(httpStatus, message);
        this.data = data;
    }
    @Builder
    public SuccessApiResponse(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
        this.data = null;
    }
}
