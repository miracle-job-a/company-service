package com.miracle.companyservice.controller.swagger;

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
@Operation(summary = "공고관리 정렬", description = "해당 기업이 공고 관리에서 정렬하는 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공 / 최신순",
                                                value = "{\"httpStatus\": 200, \"message\": \"최신순 공고 정렬\", \"data\": List<Page<ManagePostsResponseDto>> }"),
                                        @ExampleObject(
                                                name = "성공 / 마감임박순",
                                                value = "{\"httpStatus\": 200, \"message\": \"마감 임박순 공고 정렬\", \"data\": List<Page<ManagePostsResponseDto>> }"),
                                        @ExampleObject(
                                                name = "성공 / 진행중",
                                                value = "{\"httpStatus\": 200, \"message\": \"진행 중 공고만 보기\", \"data\": List<Page<ManagePostsResponseDto>> }"),
                                        @ExampleObject(
                                                name = "성공 / 마감",
                                                value = "{\"httpStatus\": 200, \"message\": \"마감 공고만 보기\", \"data\": List<Page<ManagePostsResponseDto>> }"),
                                               },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
        })
public @interface ApiManagePost {
}
