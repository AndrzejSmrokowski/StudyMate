package com.studymate.domain.progresstracking;

import java.util.Optional;

public interface ProgressRepository {
    Progress save(Progress progress);

    Optional<Progress> getProgressByUserId(String userId);
}
