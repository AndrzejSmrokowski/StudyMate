package com.studymate.domain.progresstracking;

import lombok.Builder;

@Builder
public record TestScore(
        String testId,
        double score
) {
}
