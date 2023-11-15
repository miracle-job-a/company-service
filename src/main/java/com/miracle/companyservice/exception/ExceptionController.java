package com.miracle.companyservice.exception;


import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RuntimeException.class)
    public ApiResponse runtimeHandle(RuntimeException e, HttpRequest request, BindingResult bindingResult) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("500")
                .message("서버에 문제가 생겼습니다. 다시 시도해주세요.")
                .exception("RuntimeException")
                .build();
    }

    @ExceptionHandler(value = UnauthorizedTokenException.class)
    public ApiResponse unauthorizedTokenHandle(UnauthorizedTokenException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .code("401")
                .message("토큰 값이 일치하지 않습니다.")
                .exception("UnauthorizedTokenException")
                .build();
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse notValidHandle(MethodArgumentNotValidException e, HttpRequest request, BindingResult bindingResult) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());
        FieldError error = bindingResult.getFieldError();
        String[] split = error.getDefaultMessage().split(":");
        System.out.println(split[0]);
        System.out.println(split[1]);
        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code(split[0])
                .message(split[1])
                .exception("MethodArgumentNotValidException")
                .build();
    }
}
