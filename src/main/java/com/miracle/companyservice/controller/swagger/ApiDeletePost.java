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
@Operation(summary = "공고 삭제", description = "기업이 공고를 삭제하면 Post 테이블의 deleted 컬럼을 1로 바꿔주는 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고가 성공적으로 삭제되었습니다.\", \"data\": true }")
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 공고 정보 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"해당 공고 정보가 없습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 기업 불일치",
                                                value = "{\"httpStatus\": 400, \"message\": \"companyId가 공고의 companyId 값과 다릅니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 공고 삭제 실패",
                                                value = "{\"httpStatus\": 400, \"message\": \"공고 삭제 실패\", \"data\": false }")
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiDeletePost {
}
