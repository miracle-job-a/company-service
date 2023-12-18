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
@Operation(summary = "공고 수정", description = "공고 수정 및 자소서 문항 추가/수정",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"공고가 성공적으로 수정되었습니다.\", \"data\": true }")
                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 해당 공고에 대한 정보가 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"공고 수정에 실패하였습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 자소서 문항 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"자소서 문항 수정에 실패하였습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "유효성 / 공고 타입 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_19\", \"message\": \"공고 타입 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 공고 타입 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_19\", \"message\": \"공고 타입은 NORMAL 또는 MZ 중 하나여야 합니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 공고 제목 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_15\", \"message\": \"공고 제목 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 경력 값 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_21\", \"message\": \"경력 값은 0을 포함한 양수를 허용합니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 경력 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_21\", \"message\": \"경력 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 마감일 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_16\", \"message\": \"마감일 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 마감일 값 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_16\", \"message\": \"현재 날짜보다 미래의 날짜를 설정해야 합니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 주요 업무 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_22\", \"message\": \"주요 업무 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 근무 조건 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_23\", \"message\": \"근무 조건 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 개발툴 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_17\", \"message\": \"개발툴 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 채용절차 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_24\", \"message\": \"채용절차 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 근무지 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_18\", \"message\": \"근무지 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 직무 ID Set 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_11\", \"message\": \"직무 ID 집합 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 스택 ID Set 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_11\", \"message\": \"스택 ID 집합 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 테스트 시작일 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_20\", \"message\": \"현재 날짜보다 미래의 날짜를 설정해야 합니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 / 테스트 마감일 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_20\", \"message\": \"현재 날짜보다 미래의 날짜를 설정해야 합니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),




                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiModifyPost {
}
