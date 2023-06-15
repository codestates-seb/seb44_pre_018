package com.e1i5.stackOverflow.question.repository;

import com.e1i5.stackOverflow.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
