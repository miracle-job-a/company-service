package com.miracle.companyservice.dto.request;

import com.miracle.companyservice.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionRequestDto {
    private final Long id;
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