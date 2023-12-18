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
@Operation(summary = "전체 기업의 공고 수 반환", description = "전체 기업의 진행중인 Normal/Mz 공고 수 반환",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고 수 조회 완료\", \"data\": Map<String, Long> countNormalPosts, countMZPosts }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
        })
public @interface ApiCountWholePosts {
}
