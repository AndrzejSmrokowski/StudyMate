package com.studymate.infrastructure.educationalmaterial.controller.error;

import org.springframework.http.HttpStatus;

public record EducationalMaterialErrorResponse(
        String message,
        HttpStatus status
) {
}
