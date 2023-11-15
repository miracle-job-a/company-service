package com.miracle.companyservice.exception;

import com.miracle.companyservice.dto.api.BaseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RuntimeException.class)
    public BaseApi runtimeHandle(RuntimeException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return BaseApi.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("500_1")
                .message("서버에 문제가 생겼습니다. 다시 시도해주세요.")
                .exception(e.toString())
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseApi notValidHandle(MethodArgumentNotValidException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return BaseApi.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .code("400_1")
                .message("회원정보 형식이 맞지 않거나 존재하지 않습니다.")
                .exception(e.toString())
                .build();
    }
}
