package com.miracle.companyservice.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@EqualsAndHashCode
public class CompanySignUpRequestDto {
    
    @NotBlank(message = "400_1:이메일 값이 없습니다.")
    @Email(message = "400_1:이메일 형식 오류")
    @Size(min = 1, max = 50, message = "400_1:이메일 길이가 너무 짧거나, 깁니다.")
    private final String email;

    @NotBlank(message = "400_2:기업명 값이 없습니다.")
    @Size(min = 1, max = 50, message = "400_2:기업명이 너무 짧거나, 깁니다.")
    private final String name;

    @NotBlank(message = "400_3:기업사진 업로드 되지 않았습니다.")
    @Size(min = 1, max = 50, message = "400_3:경로가 너무 짧거나, 깁니다.")
    private final String photo;

    @NotBlank(message = "400_4:비밀번호 값이 없습니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{6,}$", message = "400_4:비밀번호 형식이 맞지 않습니다.")
    @Size(min = 6, message = "400_4:비밀번호가 너무 짧습니다.")
    private final String password;

    @NotBlank(message = "400_5:사업자번호 값이 없습니다.")
    @Size(min = 1, max = 50, message = "400_5:사업자번호 길이가 너무 짧거나, 깁니다.")
    @Pattern(regexp = "\\d{3}[-]\\d{2}[-]\\d{5}", message = "400_5:사업자번호 형식에 맞지 않습니다.")
    private final String bno;

    @NotBlank(message = "400_6:대표자명 값이 없습니다.")
    @Size(min = 1, max = 50, message = "400_6:대표자명이 너무 짧거나, 깁니다.")
    private final String ceoName;

    @NotBlank(message = "400_7:업종 값이 없습니다.")
    @Size(min = 1, max = 50, message = "400_7:업종 값이 너무 짧거나, 깁니다.")
    private final String sector;

    @NotBlank(message = "400_8:주소 값이 없습니다.")
    @Size(min = 1, max = 255, message = "400_8:주소 값이 너무 짧거나, 깁니다.")
    private final String address;

    @NotBlank(message = "400_9:기업소개 값이 없습니다.")
    private final String introduction;

    @Min(value = 1, message = "400_10:재직인원이 0이하 입니다.")
    private final int employeeNum;

    @Builder
    public CompanySignUpRequestDto(String email, String name, String photo,
                                   String password, String bno,
                                   String ceoName, String sector,
                                   String address, String introduction,
                                   int employeeNum) {
        this.email = email;
        this.name = name;
        this.photo = photo;
        this.password = password;
        this.bno = bno;
        this.ceoName = ceoName;
        this.sector = sector;
        this.address = address;
        this.introduction = introduction;
        this.employeeNum = employeeNum;
    }
}
