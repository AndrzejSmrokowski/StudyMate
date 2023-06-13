package com.studymate.infrastructure.reminder.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateReminderRequest(
        @NotNull(message = "{message.not.null}")
        @NotEmpty(message = "{message.not.empty}")
        String message,
        @NotNull(message = "{time.not.null}")
        LocalDateTime reminderTime
) {
}
