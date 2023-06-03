package com.studymate.domain.testingmodule;

public class ResultsNotFoundException extends RuntimeException {
    public ResultsNotFoundException(Exam test) {
        super("Result not found for test id: " + test.examId());
    }
}
