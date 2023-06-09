package com.studymate.domain.progresstracking;

import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.testingmodule.TestResult;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;



class ProgressTrackingFacadeTest {
    private final ProgressTrackingFacade progressTrackingFacade = new ProgressTrackingFacade(new ProgressTrackingService(new InMemoryProgressRepository()));

    @Test
    void shouldInitializeProgressForNewUser() {
        // given
        String userId = "userId";

        // when
        Progress progress = progressTrackingFacade.initializeProgress(userId);

        // then
        assertAll(
                () -> assertThat(progress.reviewedMaterialsId()).hasSize(0),
                () -> assertThat(progress.testScores()).hasSize(0),
                () -> assertThat(progress.userId()).isEqualTo(userId)
        );
    }
    @Test
    void shouldAddTestScoresToProgress() {
        // given
        String userId = "userId";
        TestResult testResult = TestResult.builder()
                .score(100)
                .testId("testId")
                .userId(userId)
                .build();
        progressTrackingFacade.initializeProgress(userId);

        // when
        Progress progress = progressTrackingFacade.addTestScoreToProgress(testResult);

        // then
        assertThat(progress.testScores()).hasSize(1);

    }

    @Test
    void shouldAddReviewedMaterielsIdToProgress() {
        // given
        EducationalMaterial educationalMaterial = EducationalMaterial.builder()
                .id("materialId")
                .build();
        String userId = "userId";
        progressTrackingFacade.initializeProgress(userId);

        // when
        Progress progress = progressTrackingFacade.addReviewedMaterialIdToProgress(educationalMaterial, userId);

        // then
        assertThat(progress.reviewedMaterialsId()).hasSize(1);

    }

    @Test
    void shouldThrowProgressNotFoundExceptionWhenProgressNotFound() {
        // given
        String userId = "userId";
        EducationalMaterial educationalMaterial = EducationalMaterial.builder()
                .id("materialId")
                .build();
        // when
        Throwable thrown = catchThrowable(() -> progressTrackingFacade.addReviewedMaterialIdToProgress(educationalMaterial, userId));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(ProgressNotFoundException.class)
                .hasMessage("Progress not found for user with id: " + userId);
        
    }
    @Test
    void shouldThrowExceptionWhenMaterialAlreadyReviewed() {
        // given
        String userId = "userId";
        progressTrackingFacade.initializeProgress(userId);
        EducationalMaterial educationalMaterial = EducationalMaterial.builder()
                .id("materialId")
                .build();
        progressTrackingFacade.addReviewedMaterialIdToProgress(educationalMaterial, userId);

        // when
        Throwable thrown = catchThrowable(() -> progressTrackingFacade.addReviewedMaterialIdToProgress(educationalMaterial, userId));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(MaterialAlreadyReviewedException.class)
                .hasMessage("Material with this id already Reviewed: " + educationalMaterial.id());

    }
}