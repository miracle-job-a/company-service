package com.miracle.companyservice.controller.swagger;

import com.miracle.companyservice.dto.response.CommonApiResponse;
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
@Operation(summary = "계정 확인", description = "companyId에 해당하는 이메일과 비밀번호가 맞는지 체크합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"계정(이메일/비밀번호) 확인 성공\", \"data\": 13 }")
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 이메일 또는 비밀번호 불일치",
                                                value = "{\"httpStatus\": 400, \"message\": \"이메일 또는 비밀번호가 일치하지 않습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "유효성 / 이메일 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 이메일 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 형식 오류.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 이메일 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 길이가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 비밀번호 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiUserCheck {
}
