package com.miracle.companyservice.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionRequestDto {
    private String question;

    public QuestionRequestDto() {
    }

    public QuestionRequestDto(String question) {
        this.question = question;
    }
}
