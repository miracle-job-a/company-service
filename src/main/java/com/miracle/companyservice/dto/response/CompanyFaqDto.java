package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.CompanyFaq;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyFaqDto {
    private final String question;
    private final String answer;

    @Builder
    public CompanyFaqDto(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public CompanyFaqDto(CompanyFaq companyFaq) {
        this.question = companyFaq.getQuestion();
        this.answer = companyFaq.getAnswer();
    }
}
