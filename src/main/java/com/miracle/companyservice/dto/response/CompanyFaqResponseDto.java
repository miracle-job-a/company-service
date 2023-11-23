package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.CompanyFaq;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyFaqResponseDto {

    private final long id;
    private final String question;
    private final String answer;

    @Builder
    public CompanyFaqResponseDto(long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public CompanyFaqResponseDto(CompanyFaq companyFaq) {
        this.id = companyFaq.getId();
        this.question = companyFaq.getQuestion();
        this.answer = companyFaq.getAnswer();
    }
}