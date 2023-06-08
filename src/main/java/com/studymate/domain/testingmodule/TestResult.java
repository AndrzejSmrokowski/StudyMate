package com.studymate.domain.testingmodule;

import lombok.Builder;

import java.time.Instant;

@Builder
public record TestResult(
        String id,
        double score,
        String testId,
        String userId,
        Instant timestamp
) {
}
