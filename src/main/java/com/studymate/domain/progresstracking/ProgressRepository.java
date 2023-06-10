package com.studymate.domain.progresstracking;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProgressRepository extends MongoRepository<Progress, String> {
    Optional<Progress> getProgressByUserId(String userId);
}
