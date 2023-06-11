package com.studymate.domain.learningreminder;

public class ReminderNotFoundException extends RuntimeException {
    public ReminderNotFoundException(String id) {
        super(String.format("Reminder with id: %s not found", id));
    }
}
