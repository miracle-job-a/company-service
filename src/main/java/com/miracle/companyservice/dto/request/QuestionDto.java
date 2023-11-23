package com.miracle.companyservice.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionDto {
    private String question;

    public QuestionDto() {
    }

    public QuestionDto(String question) {
        this.question = question;
    }
}
