package com.studymate.domain.progresstracking;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProgressRepository implements ProgressRepository {
    Map<String, Progress> database = new ConcurrentHashMap<>();
    @Override
    public Progress save(Progress progress) {
        database.put(progress.userId(), progress);
        return progress;
    }

    @Override
    public Optional<Progress> getProgressByUserId(String userId) {
        return Optional.ofNullable(database.get(userId));
    }
}
