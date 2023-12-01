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
@Operation(summary = "공고 조건검색", description = "유저가 조건을 설정하여 검색한 결과값을 반환합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고 상세 검색 완료 - n페이지\", \"data\": List<ConditionalSearchPostResponseDto> }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "유효성 / 경력 값 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_21\", \"message\": \"경력값은 0이상의 양수입니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 마감공고 여부 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_25\", \"message\": \"마감된 공고 검색여부 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 스택 Set 초과",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_26\", \"message\": \"스택 id Set은 10을 초과할 수 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 직무 Set 초과",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_27\", \"message\": \"직무 id Set은 10을 초과할 수 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 주소 Set 초과",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_28\", \"message\": \"주소 Set은 3을 초과할 수 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiConditionalSearch {
}
