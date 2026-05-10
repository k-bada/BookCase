package com.bookcase.bookcase.question.repository;

import com.bookcase.bookcase.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
