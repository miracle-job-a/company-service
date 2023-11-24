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
@Operation(summary = "기업 공고 수 반환", description = "해당 기업의 전체 공고수, 진행중 공고수, 마감 공고수를 반환합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고 수 조회 완료\", \"data\": Map<String, Long> countAllPosts, countEndedPosts, countOpen }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),


                @ApiResponse(responseCode = "401",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "토큰 인증 실패",
                                                value = "{\"httpStatus\": 401, \"code\": \"401\", \"message\": \"토큰 값이 일치하지 않습니다.\", \"exception\": \"UnauthorizedTokenException\" }"),
                                        @ExampleObject(
                                                name = "회원 인증 실패",
                                                value = "{\"httpStatus\": 401, \"code\": \"401\", \"message\": \"회원 인증에 실패하여 정보를 요청할 수 없습니다.\", \"data\": \"false\" }"),
                                        @ExampleObject(
                                                name = "회원 정보에 빈 값이 있음",
                                                value = "{\"httpStatus\": 401, \"code\": \"401\", \"message\": \"회원 정보에 빈 값이 있어 인증을 할 수 없습니다.\", \"data\": \"false\" }"),
                                },
                        schema = @Schema(implementation = SuccessApiResponse.class)
                )),
                @ApiResponse(responseCode = "403",
                        description = "권한 없음",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "접근 권한 없음",
                                                value = "{\"httpStatus\": 403, \"code\": \"403\", \"message\": \"접근 권한이 없습니다.\", \"data\": \"false\" }"),

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
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
public @interface ApiCountPosts {
}
