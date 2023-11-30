package com.miracle.companyservice.exception;


import com.miracle.companyservice.dto.response.CommonApiResponse;
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
     * 400_11 유효성검사 - id
     * 400_12 유효성검사 - question(faq)
     * 400_13 유효성검사 - answer(faq)
     * 400_14 유효성검사 - question(Question)
     * 400_15 유효성검사 - title
     * 400_16 유효성검사 - endDate
     * 400_17 유효성검사 - tool
     * 400_18 유효성검사 - workAddress
     * 400_19 유효성검사 - postType
     * 400_20 유효성검사 - testDate
     * 400_21 유효성검사 - career
     * 400_22 유효성검사 - mainTask
     * 400_23 유효성검사 - workCondition
     * 400_24 유효성검사 - process
     * 400_25 유효성검사 - includeEnded
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonApiResponse notValidHandle(MethodArgumentNotValidException e) {
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
    public CommonApiResponse unauthorizedTokenHandle(UnauthorizedTokenException e) {
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
    public CommonApiResponse runtimeHandle(RuntimeException e, HttpRequest request) {
        log.info("[runtimeHandle] : " + e.getMessage());
        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("500")
                .message("서버에 문제가 생겼습니다. 다시 시도해주세요.")
                .exception("RuntimeException")
                .build();
    }

    /**
     * 400 클라이언트 에러
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonApiResponse illegalArgumentExceptionHandle(IllegalArgumentException e, HttpRequest request) {
        log.info("[illegalArgumentExceptionHandle] : " + e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code("400")
                .message("잘못된 요청입니다.")
                .exception("illegalArgumentExceptionHandle")
                .build();
    }
}
