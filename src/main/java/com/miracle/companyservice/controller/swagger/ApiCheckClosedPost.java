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
@Operation(summary = "마감/삭제된 공고 지원 가능 여부 조회", description = "일반 회원이 공고에 지원할 때 마감/삭제된 공고 지원을 막기 위한 지원 가능 여부 조회 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"지원 가능한 공고입니다.\", \"data\": true }"),
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 미존재 공고 아이디",
                                                value = "{\"httpStatus\": 400, \"message\": \"존재하지 않는 postId 입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 마감된 공고",
                                                value = "{\"httpStatus\": 400, \"message\": \"마감된 공고입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 삭제된 공고",
                                                value = "{\"httpStatus\": 400, \"message\": \"삭제된 공고입니다.\", \"data\": false }"),
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiCheckClosedPost {
}
