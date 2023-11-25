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
@Operation(summary = "FAQ 조회", description = "FAQ를 조회합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"FAQ 조회 성공\", \"data\": List<CompanyFaqResponseDto> }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 미존재 기업",
                                                value = "{\"httpStatus\": 400, \"message\": \"존재하지 않는 companyId 입니다.\", \"data\": false }"),
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),

                @ApiResponse(responseCode = "401",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "토큰 인증 실패",
                                        value = "{\"httpStatus\": 401, \"code\": \"401\", \"message\": \"토큰 값이 일치하지 않습니다.\", \"exception\": \"UnauthorizedTokenException\" }"
                                ),
                                schema = @Schema(implementation = ErrorApiResponse.class)
                        )),
                @ApiResponse(responseCode = "500",
                        description = "서버 에러",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "서버 에러",
                                        value = "{\"httpStatus\": 500, \"code\": \"500\", \"message\": \"서버에 문제가 생겼습니다. 다시 시도해주세요.\", \"exception\": \"RuntimeException\" }"
                                ),
                                schema = @Schema(implementation = ErrorApiResponse.class)
                        )),
        })
public @interface ApiGetFaq {
}
