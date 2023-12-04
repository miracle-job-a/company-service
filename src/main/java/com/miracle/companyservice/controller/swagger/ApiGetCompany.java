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
@Operation(summary = "기업 상세페이지 정보 반환", description = "기업 상세페이지에 보여주어야 하는 데이터 반환 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"기업 상세페이지 정보 조회 성공\", \"data\": CompanyPageResponseDto }")
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 해당 기업 정보 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"해당 기업에 대한 정보가 없습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / bno가 만료",
                                                value = "{\"httpStatus\": 400, \"message\": \"bno가 만료되었습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 진행 중인 공고 수 값 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"진행 중인 공고 수 조회에 실패하였습니다.\", \"data\": false }")
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiGetCompany {
}
