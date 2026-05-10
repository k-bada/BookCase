package com.bookcase.bookcase.question.controller;

import com.bookcase.bookcase.question.DTO.QuestionResponseDto;
import com.bookcase.bookcase.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping
    public List<QuestionResponseDto> getQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(question -> QuestionResponseDto.builder()
                        .questionId(question.getQuestionId())
                        .content(question.getContent())
                        .build())
                .toList();
    }
}
