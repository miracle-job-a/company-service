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
@Operation(summary = "공고 등록 가능 여부 확인", description = "사업자번호 만료여부와 회원가입 승인여부를 확인하여 공고 등록 가능 여부를 반환하는 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"정상 기업 회원 입니다.\", \"data\": true }"),
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 미존재 사업자 번호",
                                                value = "{\"httpStatus\": 400, \"message\": \"존재하지 않는 사업자 번호입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 만료된 사업자 번호",
                                                value = "{\"httpStatus\": 400, \"message\": \"만료된 사업자 번호입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 미승인 기업",
                                                value = "{\"httpStatus\": 400, \"message\": \"미승인 기업 입니다.\", \"data\": false }"),
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiCheckPostAuthority {
}
