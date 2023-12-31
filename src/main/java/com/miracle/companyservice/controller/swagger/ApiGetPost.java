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
@Operation(summary = "등록된 해당 공고 데이터 반환", description = "공고 상세보기 및 수정 페이지에서 CompanyInfo와 FAQ를 제외하고 노출되야 하는 데이터 반환 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"해당 공고 데이터 조회 성공\", \"data\": PostResponseDto }")
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 해당 공고 정보 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"해당 공고 정보가 없습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 기업 불일치",
                                                value = "{\"httpStatus\": 400, \"message\": \"companyId가 공고의 companyId 값과 다릅니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 해당 공고의 자소서 문항 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"해당 공고의 자기소개서 문항이 존재하지 않습니다.\", \"data\": false }")
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiGetPost {
}
