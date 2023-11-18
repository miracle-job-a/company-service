package com.miracle.companyservice.controller.swagger;

import com.miracle.companyservice.dto.response.ErrorApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "사업자 번호 확인", description = "사업자 번호 중복과 만료 여부를 조회하여 반환합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"가입 가능한 사업자 번호입니다.\", \"data\": true }"),
                                        @ExampleObject(
                                                name = "실패 / 미존재 사업자 번호",
                                                value = "{\"httpStatus\": 200, \"message\": \"존재하지 않는 사업자 번호입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 중복된 사업자 번호",
                                                value = "{\"httpStatus\": 200, \"message\": \"이미 가입된 사업자 번호입니다.\", \"data\": false }")},
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "사업자 번호 유효성 검사 오류.",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                        name = "사업자 번호 값 없음",
                                        value = "{\"httpStatus\": 400, \"code\": \"400_5\", \"message\": \"사업자번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "사업자 번호 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_5\", \"message\": \"사업자 번호 형식 오류.\", \"exception\": \"MethodArgumentNotValidException\" }"
                                        )
                                },
                                schema = @Schema(implementation = ErrorApiResponse.class)
                        )),

                @ApiResponse(responseCode = "401",
                        description = "비정상적인 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "토큰 인증 실패",
                                        value = "{\"httpStatus\": 401, \"code\": \"401\", \"message\": \"토큰 값이 일치하지 않습니다.\", \"exception\": \"UnauthorizedTokenException\" }"
                                ),
                                schema = @Schema(implementation = ErrorApiResponse.class)
                        )),
        })
public @interface ApiCheckBno {
}
