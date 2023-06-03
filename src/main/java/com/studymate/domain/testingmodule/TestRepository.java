package com.studymate.domain.testingmodule;

import java.util.Optional;

public interface TestRepository {

    Exam save(Exam exam);

    Optional<Exam> getTestById(String testId);
}
