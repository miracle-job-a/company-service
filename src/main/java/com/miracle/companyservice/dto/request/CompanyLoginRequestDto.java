package com.miracle.companyservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@EqualsAndHashCode
public class CompanyLoginRequestDto {

    @Schema(
            description = "기업 이메일 오류. 이메일 형식을 지켜야함",
            required = true,
            example = "youremail@miracle.com"
    )
    @NotBlank(message = "400_1:이메일 값이 없습니다.")
    @Email(message = "400_1:이메일 형식 오류")
    @Size(min = 1, max = 50, message = "400_1:이메일 길이가 너무 짧거나, 깁니다.")
    private final String email;

    @Schema(
            description = "기업회원 비밀번호. 반드시 값을 전송해야함",
            required = true,
            example = "austin123!"
    )
    @NotBlank(message = "400_4:비밀번호 값이 없습니다.")
    private final String password;

    public CompanyLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CompanyLoginRequestDto() {
        this.email = null;
        this.password = null;
    }

}
