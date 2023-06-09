package com.studymate.domain.testingmodule;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TestRepository extends MongoRepository<Exam, String> {

    Optional<Exam> getTestById(String examId);
}
