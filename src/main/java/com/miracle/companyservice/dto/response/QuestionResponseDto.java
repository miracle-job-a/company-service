package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class QuestionResponseDto {
    private final Long id;
    private final String question;

    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
    }
}
