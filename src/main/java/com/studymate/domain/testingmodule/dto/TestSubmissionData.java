package com.studymate.domain.testingmodule.dto;


import lombok.Builder;

import java.util.List;
@Builder
public record TestSubmissionData(
        String testId,
        String userId,
        List<AnswerData> answers
) {
}
