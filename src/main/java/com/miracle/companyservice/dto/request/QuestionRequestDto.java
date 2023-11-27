package com.miracle.companyservice.dto.request;

import com.miracle.companyservice.entity.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@ToString
public class QuestionRequestDto {
    @Schema(
            description = "Question 아이디 오류.",
            required = true,
            example = "4L"
    )
    @NotBlank(message = "400_11:Question 아이디 값이 없습니다.")
    @Positive(message = "400_11:아이디 값은 양수여야 합니다.")
    private final Long id;
    @Schema(
            description = "question 오류.",
            required = true,
            example = "자소서 문항 혹은 깃허브 레포지토리 주소 URL"
    )
    @NotBlank(message = "400_14:question 값이 없습니다.")
    private final String question;

    public QuestionRequestDto(Long id, String question) {
        this.id = id;
        this.question = question;
    }

    public QuestionRequestDto() {
        this.id = null;
        this.question = null;
    }
}