package com.miracle.companyservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class CompanyCheckBnoRequestDto {

    @NotBlank(message = "400_5:사업자번호 값이 없습니다.")
    @Size(min = 1, max = 50, message = "400_5:사업자번호 길이가 너무 짧거나, 깁니다.")
    @Pattern(regexp = "\\d{3}[-]\\d{2}[-]\\d{5}", message = "400_5:사업자번호 형식에 맞지 않습니다.")
    private final String bno;

    @JsonCreator
    public CompanyCheckBnoRequestDto(@JsonProperty("bno") String bno) {
        this.bno = bno;
    }
}
