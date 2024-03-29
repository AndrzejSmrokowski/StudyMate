package com.studymate.domain.learningreminder;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder(toBuilder = true)
public record Reminder(
        String id,
        String userId,
        String message,
        LocalDateTime reminderTime,
        boolean sent
) {
}
