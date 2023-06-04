package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.TestData;

public class TestMapper {
    public static Exam mapToExam(TestData testData) {
        return Exam.builder()
                .examName(testData.testName())
                .questions(QuestionMapper.mapToQuestions(testData.questions()))
                .build();
    }
}
