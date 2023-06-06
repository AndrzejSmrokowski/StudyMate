package com.studymate.domain.progresstracking;

import lombok.Builder;

import java.util.List;

@Builder
public record Progress(
        String userId,
        List<TestScore> testScores,
        List<String> reviewedMaterialsId
) {
}
