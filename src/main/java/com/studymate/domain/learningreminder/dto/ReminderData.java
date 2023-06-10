package com.studymate.domain.learningreminder.dto;

import java.time.LocalDateTime;

public record ReminderData(
        String userId,
        String message,
        LocalDateTime reminderTime
) {
}
