package com.miracle.companyservice.exception;


import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     *
     * 400_1 유효성검사 - email
     * 400_2 유효성검사 - name
     * 400_3 유효성검사 - photo
     * 400_4 유효성검사 - password
     * 400_5 유효성검사 - bno
     * 400_6 유효성검사 - ceoName
     * 400_7 유효성검사 - sector
     * 400_8 유효성검사 - address
     * 400_9 유효성검사 - introduction
     * 400_10 유효성검사 - employeeNum
     *
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse notValidHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.info("[notValidHandle] : " + e.getMessage());
        FieldError error = bindingResult.getFieldError();
        String[] split = error.getDefaultMessage().split(":");

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code(split[0])
                .message(split[1])
                .exception("MethodArgumentNotValidException")
                .build();
    }

    /**
     * 401_1 토큰 인증
     */
    @ExceptionHandler(value = UnauthorizedTokenException.class)
    public ApiResponse unauthorizedTokenHandle(UnauthorizedTokenException e) {
        log.info("[unauthorizedTokenHandle] : " + e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .code("401")
                .message("토큰 값이 일치하지 않습니다.")
                .exception("UnauthorizedTokenException")
                .build();
    }


    /**
     * 500 서버에러
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ApiResponse runtimeHandle(RuntimeException e) {
        log.info("[runtimeHandle] : " + e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("500")
                .message("서버에 문제가 생겼습니다. 다시 시도해주세요.")
                .exception("RuntimeException")
                .build();
    }
}
