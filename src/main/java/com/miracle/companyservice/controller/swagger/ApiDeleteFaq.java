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
@Operation(summary = "FAQ 삭제", description = "FAQ를 삭제합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"FAQ 삭제 성공\", \"data\": true }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 미존재 FAQ",
                                                value = "{\"httpStatus\": 400, \"message\": \"존재하지 않는 faqId 입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 기업 아이디 불일치 ",
                                                value = "{\"httpStatus\": 400, \"code\": \"400\", \"message\": \"companyId와 삭제하려는 faq의 companyId가 일치하지 않습니다.\", \"data\": \"false\" }"),
                                           },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiDeleteFaq {
}
