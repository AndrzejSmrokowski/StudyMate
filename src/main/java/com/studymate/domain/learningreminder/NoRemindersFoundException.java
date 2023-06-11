package com.studymate.domain.learningreminder;

public class NoRemindersFoundException extends RuntimeException {
    public NoRemindersFoundException(String userId) {
        super(String.format("No reminders found for user with id: %s", userId));
    }
}
