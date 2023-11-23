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
            description = "companyId는 양수만 가능",
            required = true,
            example = "3"
    )
    @Positive(message = "400_11:companyId 값이 0보다 작습니다.")
    private final Long companyId;

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

    public CompanyFaqRequestDto(Long companyId, String question, String answer) {
        this.companyId = companyId;
        this.question = question;
        this.answer = answer;
    }

    public CompanyFaqRequestDto() {
        this.companyId = null;
        this.question = null;
        this.answer = null;
    }

}
