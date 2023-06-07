package com.studymate.infrastructure.user.controller.error;

import org.springframework.http.HttpStatus;

public record TokenErrorResponse(String message,
                                 HttpStatus status) {
}
