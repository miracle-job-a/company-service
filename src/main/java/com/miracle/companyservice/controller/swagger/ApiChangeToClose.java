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
@Operation(summary = "공고 마감", description = "요청한 공고를 마감처리 합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고가 마감처리 되었습니다.\", \"data\": true }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 기업 불일치",
                                                value = "{\"httpStatus\": 400, \"message\": \"companyId가 공고의 companyId 값과 다릅니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 미존재 공고 ",
                                                value = "{\"httpStatus\": 400, \"code\": \"400\", \"message\": \"공고가 존재하지 않습니다.\", \"data\": \"false\" }"),
                                           },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiChangeToClose {
}
