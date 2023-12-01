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
@Operation(summary = "기업 회원 정보 수정", description = "기업페이지 기업 회원 정보 수정 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"기업 회원 정보 수정 성공\", \"data\": true }")
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
                                                value = "{\"httpStatus\": 400, \"message\": \"해당 공고 정보가 없습니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 자소서 문항 없음",
                                                value = "{\"httpStatus\": 400, \"message\": \"해당 공고에 대한 자기소개서 문항이 존재하지 않습니다.\", \"data\": false }"),

                                        @ExampleObject(
                                                name = "유효성 /기업명 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_2\", \"message\": \"기업명 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /기업명 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_2\", \"message\": \"기업명 길이가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /사진 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_3\", \"message\": \"사진이 업로드 되지 않았습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /사진 경로 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_3\", \"message\": \"사진 경로가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /비밀번호 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /비밀번호 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호 형식이 맞지 않습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /비밀번호 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호가 너무 짧습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /대표자명 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_6\", \"message\": \"대표자명 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /대표자명 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_6\", \"message\": \"대표자명이 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /업종 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"업종 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /업종 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"업종 값이 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /주소 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_6\", \"message\": \"주소 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /주소 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"주소 값이 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /기업소개 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"기업소개 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /기업소개 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"기업소개 값이 너무 짧습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "유효성 /재직인원 값 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"재직인원이 0이하 입니다.\", \"exception\": \"MethodArgumentNotValidException\" }")
                                },
                                schema = @Schema(implementation = CommonApiResponse.class)
                        )),
        })
public @interface ApiModifyCompanyInfo {
}