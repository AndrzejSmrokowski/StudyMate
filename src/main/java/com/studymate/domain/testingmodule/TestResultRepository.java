package com.studymate.domain.testingmodule;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TestResultRepository extends MongoRepository<TestResult, String> {
    Optional<List<TestResult>> getResultsByTestId(String testId);
    Optional<List<TestResult>> getResultsByUserId(String userId);

}
