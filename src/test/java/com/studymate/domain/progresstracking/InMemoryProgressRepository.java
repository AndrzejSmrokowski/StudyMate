package com.studymate.domain.progresstracking;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryProgressRepository implements ProgressRepository {
    Map<String, Progress> database = new ConcurrentHashMap<>();


    @Override
    public Optional<Progress> getProgressByUserId(String userId) {
        return Optional.ofNullable(database.get(userId));
    }

    @Override
    public <S extends Progress> S save(S entity) {
        database.put(entity.userId(), entity);
        return entity;
    }

    @Override
    public <S extends Progress> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Progress> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Progress> findAll() {
        return null;
    }

    @Override
    public Iterable<Progress> findAllById(Iterable<String> strings) {
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
    public void delete(Progress entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Progress> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Progress> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Progress> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Progress> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Progress> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Progress> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Progress> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Progress> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Progress> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Progress> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Progress> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Progress, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
