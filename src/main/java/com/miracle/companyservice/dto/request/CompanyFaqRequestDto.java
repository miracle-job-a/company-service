package com.miracle.companyservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ToString
@Getter
public class CompanyFaqRequestDto {

    @Schema(
            description = "질문은 공백, null 허용하지 않음",
            required = true,
            example = "공고와 관련한 추가 문의는 어떻게 할 수 있나요?"
    )
    @NotBlank(message = "400_12:질문 값이 없습니다.")
    private final String question;

    @Schema(
            description = "답변은 공백, null 허용하지 않음",
            required = true,
            example = "추가 문의는 googleHR@google.com으로 해주세요."
    )
    @NotBlank(message = "400_13:답변 값이 없습니다.")
    private final String answer;

    public CompanyFaqRequestDto(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public CompanyFaqRequestDto() {
        this.question = null;
        this.answer = null;
    }

}
