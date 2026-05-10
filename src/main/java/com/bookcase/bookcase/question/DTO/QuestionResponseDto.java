package com.bookcase.bookcase.question.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponseDto {

    private Long questionId;
    private String content;
}
