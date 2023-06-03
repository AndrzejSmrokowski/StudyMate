package com.studymate.domain.testingmodule.dto;

import com.studymate.domain.testingmodule.AnswerChoice;

public record AnswerData(
        String questionId,
        AnswerChoice answerChoice

) {
}
