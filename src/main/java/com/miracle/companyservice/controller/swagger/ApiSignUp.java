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
@Operation(summary = "회원가입", description = "회원 정보 요청을 받아 처리합니다.",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"사용가능한 이메일입니다.\", \"data\": true }")

                                },
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "비정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "실패 / 중복된 이메일",
                                                value = "{\"httpStatus\": 400, \"message\": \"중복된 이메일입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 미존재 사업자번호",
                                                value = "{\"httpStatus\": 400, \"message\": \"존재하지 않는 사업자 번호입니다.\", \"data\": false }"),
                                        @ExampleObject(
                                                name = "실패 / 가입된 사업자번호",
                                                value = "{\"httpStatus\": 400, \"message\": \"이미 가입된 사업자 번호입니다.\", \"data\": false }"),

                                        @ExampleObject(
                                        name = "유효성 / 이메일 값 없음",
                                        value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /이메일 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 형식 오류.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /이메일 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 길이가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

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
                                                name = "유효성 /사업자 번호 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_5\", \"message\": \"사업자번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "유효성 /사업자 번호 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_5\", \"message\": \"사업자 번호 형식 오류.\", \"exception\": \"MethodArgumentNotValidException\" }"),

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
public @interface ApiSignUp {
}
