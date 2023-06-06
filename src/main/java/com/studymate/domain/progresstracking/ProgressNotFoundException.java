package com.studymate.domain.progresstracking;

public class ProgressNotFoundException extends RuntimeException {
    public ProgressNotFoundException(String userId) {
        super("Progress not found for user with id: " + userId);
    }
}
