package com.studymate.domain.progresstracking;

import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.testingmodule.TestResult;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProgressTrackingFacade {

    private final ProgressTrackingService progressTrackingService;

    public Progress addTestScoreToProgress(TestResult testResult) {
        Progress progress = progressTrackingService.getProgressByUserId(testResult.userId());
        List<TestScore> scores = progress.testScores();
        scores.add(TestResultMapper.mapToTestScores(testResult));
        return progressTrackingService.updateProgress(Progress.builder()
                .userId(progress.userId())
                .reviewedMaterialsId(progress.reviewedMaterialsId())
                .testScores(scores)
                .build());
    }

    public Progress addReviewedMaterialIdToProgress(EducationalMaterial educationalMaterial, String userId) {
        Progress progress = progressTrackingService.getProgressByUserId(userId);
        List<String> reviewedMaterialsId = progress.reviewedMaterialsId();
        if (reviewedMaterialsId.contains(educationalMaterial.id())) {
            throw new MaterialAlreadyReviewedException(educationalMaterial.id());
        }

        reviewedMaterialsId.add(educationalMaterial.id());

        return progressTrackingService.updateProgress(Progress.builder()
                .userId(progress.userId())
                .testScores(progress.testScores())
                .reviewedMaterialsId(reviewedMaterialsId)
                .build());
    }

    public Progress initializeProgress(String userId) {
        Progress progress = Progress.builder()
                .userId(userId)
                .reviewedMaterialsId(new ArrayList<>())
                .testScores(new ArrayList<>())
                .build();
        return progressTrackingService.updateProgress(progress);
    }

    public Progress getProgressByUserId(String userId) {
        return progressTrackingService.getProgressByUserId(userId);
    }
}
