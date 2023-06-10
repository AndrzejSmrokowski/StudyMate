package com.studymate.infrastructure.progress.controller.error;

import org.springframework.http.HttpStatus;

public record ProgressErrorResponse(
        String message,
        HttpStatus status
) {
}
