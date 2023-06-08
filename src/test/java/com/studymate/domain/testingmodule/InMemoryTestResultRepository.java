package com.studymate.domain.testingmodule;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryTestResultRepository implements TestResultRepository {
    Map<String, TestResult> database = new ConcurrentHashMap<>();

    @Override
    public Optional<List<TestResult>> getResultsByTestId(String testId) {
        List<TestResult> results = database.values()
                .stream()
                .filter(result -> result.testId().equals(testId))
                .collect(Collectors.toList());
        return Optional.ofNullable(results.isEmpty() ? null : results);
    }

    @Override
    public Optional<List<TestResult>> getResultsByUserId(String userId) {
        List<TestResult> results = database.values()
                .stream()
                .filter(result -> result.userId().equals(userId))
                .collect(Collectors.toList());
        return Optional.ofNullable(results.isEmpty() ? null : results);
    }

    @Override
    public <S extends TestResult> S save(S entity) {
        String id = UUID.randomUUID().toString();

        TestResult testResult = TestResult.builder()
                .id(id)
                .testId(entity.testId())
                .score(entity.score())
                .timestamp(entity.timestamp())
                .userId(entity.userId())
                .build();

        database.put(id, testResult);

        return (S) testResult;
    }

    @Override
    public <S extends TestResult> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TestResult> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<TestResult> findAll() {
        return null;
    }

    @Override
    public Iterable<TestResult> findAllById(Iterable<String> strings) {
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
    public void delete(TestResult entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends TestResult> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<TestResult> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TestResult> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TestResult> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends TestResult> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends TestResult> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TestResult> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TestResult> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TestResult> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TestResult> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TestResult> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TestResult, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
