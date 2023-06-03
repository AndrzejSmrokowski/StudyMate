package com.studymate.domain.testingmodule.dto;

import com.studymate.domain.testingmodule.dto.AnswerData;

import java.util.List;

public record TestSubmissionData(
        String testId,
        String userId,
        List<AnswerData> answers
) {
}
