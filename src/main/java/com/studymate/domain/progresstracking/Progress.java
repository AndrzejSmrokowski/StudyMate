package com.studymate.domain.progresstracking;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document
public record Progress(
        @Id String userId,
        List<TestScore> testScores,
        List<String> reviewedMaterialsId
) {
}
