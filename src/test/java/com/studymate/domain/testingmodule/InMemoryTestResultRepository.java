package com.studymate.domain.testingmodule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTestResultRepository implements TestResultRepository {
    Map<String, List<TestResult>> database = new ConcurrentHashMap<>();
    @Override
    public TestResult save(TestResult testResult) {
        if (database.containsKey(testResult.testId())) {
            List<TestResult> results = database.get(testResult.testId());
            results.add(testResult);
            database.put(testResult.testId(), results);
            return testResult;
        }
        List<TestResult> results = new ArrayList<>();
        results.add(testResult);
        database.put(testResult.testId(), results);
        return testResult;
    }

    @Override
    public Optional<List<TestResult>> getResultsByTest(Exam exam) {
        List<TestResult> results = database.get(exam.examId());
        return Optional.ofNullable(results);
    }

    @Override
    public Optional<List<TestResult>> getResultsByUserId(String userId) {
        return Optional.empty();
    }
}
