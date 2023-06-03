package com.studymate.domain.testingmodule;

import lombok.Builder;

import java.util.List;

@Builder
public record Question(
        String questionId,
        String questionText,
        List<String> options,
        AnswerChoice correctAnswer
) {
}
