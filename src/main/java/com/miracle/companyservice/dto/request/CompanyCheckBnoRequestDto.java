package com.miracle.companyservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
public class CompanyCheckBnoRequestDto {

    @Schema(
            description = "사업자 번호. 'nnn-nn-nnnnn' 형식으로 요청해야함.",
            required = true,
            example = "111-22-33333"
    )
    @NotBlank(message = "400_5:사업자번호 값이 없습니다.")
    @Pattern(regexp = "\\d{3}[-]\\d{2}[-]\\d{5}", message = "400_5:사업자번호 형식에 맞지 않습니다.")
    private final String bno;

    @JsonCreator
    public CompanyCheckBnoRequestDto(@JsonProperty("bno") String bno) {
        this.bno = bno;
    }
}
