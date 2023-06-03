package com.studymate.domain.testingmodule.dto;

import com.studymate.domain.testingmodule.AnswerChoice;

import java.util.List;

public record QuestionData(
        String questionId,
        String questionText,
        List<String> options,
        AnswerChoice correctAnswer
) {
}
