package com.studymate.infrastructure.testingmodule.controller.error;

import org.springframework.http.HttpStatus;

public record TestingModuleErrorResponse(
        String message,
        HttpStatus status
) {
}
