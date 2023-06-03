package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.TestData;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;

    public Exam createTest(TestData testData) {
        return testRepository.save(TestMapper.mapToExam(testData));
    }

    public Exam getTestById(String testId) {
        return testRepository.getTestById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    }
    public List<TestResult> getTestResults(String testId) {
        Exam test = testRepository.getTestById(testId).orElseThrow(() -> new TestNotFoundException(testId));
        return testResultRepository.getResultsByTest(test).orElseThrow(() -> new ResultsNotFoundException(test));
    }

    public TestResult saveResult(TestResult result) {
        return testResultRepository.save(result);
    }
}
