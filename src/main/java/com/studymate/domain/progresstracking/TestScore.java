package com.studymate.domain.progresstracking;

import lombok.Builder;

import java.time.Instant;

@Builder
public record TestScore(
        String testId,
        double score,
        Instant timestamp
) {
}
