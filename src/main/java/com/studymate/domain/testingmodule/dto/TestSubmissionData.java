package com.studymate.domain.testingmodule.dto;


import java.util.List;

public record TestSubmissionData(
        String testId,
        String userId,
        List<AnswerData> answers
) {
}
