package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.TestData;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<TestResult> getTestResults(String testId, String userId) {
        Exam test = testRepository.getTestById(testId).orElseThrow(() -> new TestNotFoundException(testId));
        List<TestResult> results = testResultRepository.getResultsByTestId(test.id()).orElseThrow(() -> new ResultsNotFoundException(test));
        return results.stream()
                .filter(result -> result.userId().equals(userId))
                .collect(Collectors.toList());

    }

    public TestResult saveResult(TestResult result) {
        return testResultRepository.save(result);
    }

    public void deleteTest(String testId) {
        Exam exam = testRepository.getTestById(testId).orElseThrow(() -> new TestNotFoundException(testId));
        testRepository.delete(exam);
    }
}
