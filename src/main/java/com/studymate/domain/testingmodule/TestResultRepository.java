package com.studymate.domain.testingmodule;

import java.util.List;
import java.util.Optional;

public interface TestResultRepository {

    TestResult save(TestResult testResult);

    Optional<List<TestResult>> getResultsByTest(Exam exam);
    Optional<List<TestResult>> getResultsByUserId(String userId);


}
