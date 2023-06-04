package com.studymate.domain.testingmodule.dto;

import java.util.List;

public record TestData(
        String testName,
        List<QuestionData> questions

) {
}
