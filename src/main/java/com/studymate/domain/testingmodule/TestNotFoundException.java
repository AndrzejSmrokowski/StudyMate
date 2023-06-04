package com.studymate.domain.testingmodule;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(String testId) {
        super("Test not found with ID: " + testId);
    }
}
