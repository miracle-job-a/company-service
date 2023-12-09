package com.miracle.companyservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
public class CompanyInfoRequestDto {
    @Schema(
            description = "기업명. 1글자 이상이어야 함.",
            required = true,
            example = "미라클"
    )
    @Size(min = 1, max = 50, message = "400_2:기업명이 너무 짧거나, 깁니다.")
    private final String name;

    @Schema(
            description = "대표자명. 2글자 이상, 한글만 가능합니다.",
            required = true,
            example = "이병건"
    )
    @Size(min = 1, max = 50, message = "400_6:대표자명이 너무 짧거나, 깁니다.")
    @Pattern(regexp = "^[가-힣]{2,}$", message = "400_6:대표자명이 형식에 맞지 않습니다.")
    private final String ceoName;

    @Schema(
            description = "재직인원. 최소 1 이상이여야 함.",
            required = true,
            example = "300"
    )
    @Min(value = 1, message = "400_10:재직인원이 0이하 입니다.")
    private final int employeeNum;

    @Schema(
            description = "기업 업종. 업종 명을 반드시 입력해야 합니다.",
            required = true,
            example = "소프트웨어 개발업"
    )
    @Size(min = 1, max = 50, message = "400_7:업종 값이 너무 짧거나, 깁니다.")
    private final String sector;

    @Schema(
            description = "기업 사진. 반드시 기업사진을 업로드해야 합니다.",
            required = true,
            example = "기업로고.jpg"
    )
    @Size(min = 1, max = 50, message = "400_3:사진 경로가 너무 짧거나, 깁니다.")
    private final String photo;

    @Schema(
            description = "기업 소개 글. 최소 10글자 이상의 소개글이 있어야 합니다.",
            required = true,
            example = "시리즈A 투자를 받은 유망한 기업입니다."
    )
    @Size(min = 20,  message = "400_0:기업소개 값이 너무 짧습니다.")
    private final String introduction;

    @Schema(
            description = "기업 주소",
            required = true,
            example = "서울특별시 서초구 효령로 113"
    )
    @Size(min = 1, max = 255, message = "400_8:주소 값이 너무 짧거나, 깁니다.")
    private final String address;

    @Schema(
            description = "기업회원 새로운 비밀번호. 최소 6글자이며 특수문자가 최소 1개 포함되어야함",
            required = true,
            example = "austin123!"
    )
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{6,}$", message = "400_4:비밀번호 형식이 맞지 않습니다.")
    @Size(min = 6, message = "400_4:비밀번호가 너무 짧습니다.")
    private final String pwd;

    public CompanyInfoRequestDto(String name, String ceoName, int employeeNum, String sector, String photo, String introduction, String address, String pwd) {
        this.name = name;
        this.ceoName = ceoName;
        this.employeeNum = employeeNum;
        this.sector = sector;
        this.photo = photo;
        this.introduction = introduction;
        this.address = address;
        this.pwd = pwd;

    }

    public CompanyInfoRequestDto() {
        this.name = null;
        this.ceoName = null;
        this.employeeNum = 0;
        this.sector = null;
        this.photo = null;
        this.introduction = null;
        this.address = null;
        this.pwd = null;
    }
}
