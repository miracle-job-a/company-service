package com.miracle.companyservice.exception;


import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * Runtime handle api response.
     *
     * @param e       the e
     * @param request the request
     * @return the api response
     * @author Kade-jeon
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ApiResponse runtimeHandle(RuntimeException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("500")
                .message("서버에 문제가 생겼습니다. 다시 시도해주세요.")
                .exception(e.toString())
                .build();
    }

    /**
     * Unauthorized token handle api response.
     *
     * @param e       the e
     * @param request the request
     * @return the api response
     * @author Kade-jeon
     */
    @ExceptionHandler(value = UnauthorizedTokenException.class)
    public ApiResponse unauthorizedTokenHandle(RuntimeException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .code("401")
                .message("토큰 값이 일치하지 않습니다.")
                .exception(e.toString())
                .build();
    }

    /**
     * Not valid handle api response.
     *
     * @param e       the e
     * @param request the request
     * @return the api response
     * @author Kade-jeon
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse notValidHandle(MethodArgumentNotValidException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}",request.getURI(), request.getMethod(),request.getMethodValue());
        log.info(e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code("400_2")
                .message("회원정보 형식이 맞지 않거나 존재하지 않습니다.")
                .exception(e.toString())
                .build();
    }

    /**
     * Illegal argument exception handle api response.
     *
     * @param e       the e
     * @param request the request
     * @return the api response
     * @author wjdals3936
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ApiResponse illegalArgumentExceptionHandle(IllegalArgumentException e, HttpRequest request) {
        log.info("[ExceptionController] uri: {}, method: {}, methodValue: {}", request.getURI(), request.getMethod(), request.getMethodValue());
        log.info(e.getMessage());

        return ErrorApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code("400")
                .message("잘못된 요청입니다.")
                .exception(e.toString())
                .build();
    }
}
