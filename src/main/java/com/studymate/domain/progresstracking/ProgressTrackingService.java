package com.studymate.domain.progresstracking;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProgressTrackingService {

    private final ProgressRepository progressRepository;
    public Progress updateProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    public Progress getProgressByUserId(String userId) {
        return progressRepository.getProgressByUserId(userId).orElseThrow(() -> new ProgressNotFoundException(userId));
    }
}
