package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.TestData;
import com.studymate.domain.testingmodule.dto.TestSubmissionData;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestingModuleFacade {
    private final TestService testService;
    private final TestSolver testSolver;

    public Exam createTest(TestData testData) {
        return testService.createTest(testData);
    }

    public TestResult solveTest(String testId, TestSubmissionData submissionData) {
        Exam exam = getTestById(testId);
        TestResult result = testSolver.calculateResult(exam, submissionData);
        return testService.saveResult(result);
    }

    public Exam getTestById(String testId) {
        return testService.getTestById(testId);
    }

    public List<TestResult> getTestResults(String testId) {
        return testService.getTestResults(testId);
    }
}
