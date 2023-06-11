package com.studymate.infrastructure.reminder.controller.error;

import org.springframework.http.HttpStatus;

public record ReminderErrorResponse(
        String message,
        HttpStatus status
) {
}
