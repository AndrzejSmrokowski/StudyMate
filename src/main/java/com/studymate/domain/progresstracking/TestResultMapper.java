package com.studymate.domain.progresstracking;

import com.studymate.domain.testingmodule.TestResult;

public class TestResultMapper {
    public static TestScore mapToTestScores(TestResult testResult) {
        return TestScore.builder()
                .testId(testResult.testId())
                .score(testResult.score())
                .build();


    }
}
