package com.studymate.domain.testingmodule;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTestRepository implements TestRepository{

    Map<String, Exam> database = new ConcurrentHashMap<>();
    @Override
    public Exam save(Exam entity) {
        String id;
        if (database.values().stream().anyMatch(test -> test.examId().equals(entity.examId()))) {
            id = entity.examId();
        } else {
            id = UUID.randomUUID().toString();
        }

        Exam savedExam = Exam.builder()
                .examId(id)
                .examName(entity.examName())
                .questions(entity.questions())
                .build();

        database.put(id, savedExam);
        return savedExam;
    }

    @Override
    public Optional<Exam> getTestById(String examId) {
        return Optional.ofNullable(database.get(examId));
    }
}
