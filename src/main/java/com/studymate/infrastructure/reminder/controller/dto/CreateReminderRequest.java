package com.studymate.infrastructure.reminder.controller.dto;

import java.time.LocalDateTime;

public record CreateReminderRequest(
        String message,
        LocalDateTime reminderTime
) {
}
