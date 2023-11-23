package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionResponseDto {
    private final String question;

    public QuestionResponseDto(String question) {
        this.question = question;
    }

    public QuestionResponseDto(Question question){
        this.question = question.getQuestion();
    }

}