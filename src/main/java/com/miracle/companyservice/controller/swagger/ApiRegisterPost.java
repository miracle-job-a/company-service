package com.miracle.companyservice.controller.swagger;

import com.miracle.companyservice.dto.response.CommonApiResponse;
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
@Operation(summary = "공고 등록", description = "기업이 공고를 등록하는 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고가 성공적으로 등록되었습니다.\", \"data\": true }")
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 공고 등록 실패",
                                                value = "{\"httpStatus\": 400, \"message\": \"공고 등록에 실패하였습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 자소서 문항 등록 실패",
                                                value = "{\"httpStatus\": 400, \"message\": \"자기소개서 문항 등록에 실패하였습니다.\", \"data\": false }")/*,
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
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),*/
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiRegisterPost {
}
