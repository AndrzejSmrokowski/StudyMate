package com.studymate.domain.testingmodule;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryTestRepository implements TestRepository{

    Map<String, Exam> database = new ConcurrentHashMap<>();


    @Override
    public Optional<Exam> getTestById(String examId) {
        return Optional.ofNullable(database.get(examId));
    }

    @Override
    public <S extends Exam> S save(S entity) {
        String id;
        if (database.values().stream().anyMatch(test -> test.id().equals(entity.id()))) {
            id = entity.id();
        } else {
            id = UUID.randomUUID().toString();
        }

        Exam savedExam = Exam.builder()
                .id(id)
                .examName(entity.examName())
                .questions(entity.questions())
                .build();

        database.put(id, savedExam);
        return (S) savedExam;
    }

    @Override
    public <S extends Exam> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Exam> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Exam> findAll() {
        return null;
    }

    @Override
    public Iterable<Exam> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Exam entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Exam> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Exam> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Exam> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Exam> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Exam> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Exam> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Exam> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Exam> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Exam> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Exam> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Exam> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Exam, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
