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
@Operation(summary = "공고 아이디 Set에 따른 해당 기업명 조회", description = "기업명을 조회합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"기업명 조회 성공\", \"data\": List<CompanyNameResponseDto> }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 공고아이디 집합 값 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"공고아이디 집합에 값이 없습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 공고 정보 조회 실패",
                                                value = "{\"httpStatus\": 400, \"message\": \"공고 정보가 없습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 기업 정보 조회 실패",
                                                value = "{\"httpStatus\": 400, \"message\": \"기업 정보가 없습니다.\", \"data\": false }")
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiGetCompanyName {
}
