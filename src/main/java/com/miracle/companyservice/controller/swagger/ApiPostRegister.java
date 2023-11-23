package com.miracle.companyservice.controller.swagger;

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
@Operation(summary = "공고 등록", description = "기업이 공고를 등록하는 API",
        responses = {
                @ApiResponse(responseCode = "200",
                        description = "정상 요청",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = "{\"httpStatus\": 200, \"message\": \"SUCCESS\", \"data\": {\n" +
                                                        "    \"httpStatus\": 200,\n" +
                                                        "    \"message\": \"SUCCESS\",\n" +
                                                        "    \"data\": {\n" +
                                                        "        \"name\": \"오라클코리아\",\n" +
                                                        "        \"ceoName\": \"오스틴 강\",\n" +
                                                        "        \"photo\": \"/사진/저장/경로.jpg\",\n" +
                                                        "        \"employeeNum\": 3000,\n" +
                                                        "        \"address\": \"서울 서초구 효령로 335\",\n" +
                                                        "        \"introduction\": \"데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.\",\n" +
                                                        "        \"faqList\": [\n" +
                                                        "            {\n" +
                                                        "                \"question\": \"자주 묻는 질문 1\",\n" +
                                                        "                \"answer\": \"답변 1\"\n" +
                                                        "            },\n" +
                                                        "            {\n" +
                                                        "                \"question\": \"자주 묻는 질문 2\",\n" +
                                                        "                \"answer\": \"답변 2\"\n" +
                                                        "            },\n" +
                                                        "            {\n" +
                                                        "                \"question\": \"자주 묻는 질문 3\",\n" +
                                                        "                \"answer\": \"답변 3\"\n" +
                                                        "            },\n" +
                                                        "            {\n" +
                                                        "                \"question\": \"자주 묻는 질문 1\",\n" +
                                                        "                \"answer\": \"답변 1\"\n" +
                                                        "            }\n" +
                                                        "        ]\n" +
                                                        "    }\n" +
                                                        "} }"),
                                        @ExampleObject(
                                                name = "실패 / 중복된 이메일",
                                                value = "{\"httpStatus\": 200, \"message\": \"중복된 이메일입니다.\", \"data\": false }")},
                                schema = @Schema(implementation = SuccessApiResponse.class)
                        )),
                @ApiResponse(responseCode = "400",
                        description = "유효성 검사 오류",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                        name = "이메일 값 없음",
                                        value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "이메일 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 형식 오류.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "이메일 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_1\", \"message\": \"이메일 길이가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "기업명 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_2\", \"message\": \"기업명 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "기업명 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_2\", \"message\": \"기업명 길이가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "사진 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_3\", \"message\": \"사진이 업로드 되지 않았습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "사진 경로 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_3\", \"message\": \"사진 경로가 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "비밀번호 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "비밀번호 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호 형식이 맞지 않습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "비밀번호 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_4\", \"message\": \"비밀번호가 너무 짧습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "사업자 번호 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_5\", \"message\": \"사업자번호 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "사업자 번호 형식 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_5\", \"message\": \"사업자 번호 형식 오류.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "대표자명 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_6\", \"message\": \"대표자명 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "대표자명 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_6\", \"message\": \"대표자명이 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "업종 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"업종 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "업종 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"업종 값이 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "주소 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_6\", \"message\": \"주소 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "주소 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"주소 값이 너무 짧거나, 깁니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "기업소개 값 없음",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"기업소개 값이 없습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),
                                        @ExampleObject(
                                                name = "기업소개 길이 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"기업소개 값이 너무 짧습니다.\", \"exception\": \"MethodArgumentNotValidException\" }"),

                                        @ExampleObject(
                                                name = "재직인원 값 오류",
                                                value = "{\"httpStatus\": 400, \"code\": \"400_7\", \"message\": \"재직인원이 0이하 입니다.\", \"exception\": \"MethodArgumentNotValidException\" }")
                                },
                                schema = @Schema(implementation = ErrorApiResponse.class)
                        )),

                @ApiResponse(responseCode = "401",
                        description = "비정상적인 요청",
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
public @interface ApiPostRegister {
}
