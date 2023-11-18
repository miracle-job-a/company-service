package com.miracle.companyservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
public class CompanyCheckEmailRequestDto {

    @Schema(
            description = "기업 이메일 오류. 이메일 형식을 지켜야함",
            required = true,
            example = "youremail@miracle.com"
    )
    @NotBlank(message = "400_1:이메일 값이 없습니다.")
    @Email(message = "400_1:이메일 형식 오류")
    @Size(min = 1, max = 50, message = "400_1:이메일 길이가 너무 짧거나, 깁니다.")
    private final String email;

    @JsonCreator
    public CompanyCheckEmailRequestDto(@JsonProperty("email") String email) {
        this.email = email;
    }
}
