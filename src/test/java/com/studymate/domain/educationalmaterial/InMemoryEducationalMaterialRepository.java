package com.studymate.domain.educationalmaterial;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryEducationalMaterialRepository implements EducationalMaterialRepository{

    Map<String, EducationalMaterial> database = new ConcurrentHashMap<>();

    @Override
    public <S extends EducationalMaterial> List<S> saveAll(Iterable<S> entities) {
        return null;
    }


    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<EducationalMaterial> findAll() {
        return database.values().stream().toList();
    }

    @Override
    public Iterable<EducationalMaterial> findAllById(Iterable<String> strings) {
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
    public void delete(EducationalMaterial entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends EducationalMaterial> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<EducationalMaterial> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<EducationalMaterial> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends EducationalMaterial> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends EducationalMaterial> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends EducationalMaterial, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends EducationalMaterial> S save(S entity) {
        String id;
        if (database.values().stream().anyMatch(material -> material.id().equals(entity.id()))) {
            id = entity.id();
        } else {
            id = UUID.randomUUID().toString();
        }

         EducationalMaterial savedEducationalMaterial = EducationalMaterial.builder()
                 .id(id)
                 .title(entity.title())
                 .description(entity.description())
                 .content(entity.content())
                 .comments(entity.comments())
                 .status(entity.status())
                 .likes(entity.likes())
                 .likedBy(entity.likedBy())
                 .build();

         database.put(id, savedEducationalMaterial);
        return (S) savedEducationalMaterial;
    }

    @Override
    public Optional<EducationalMaterial> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }
}
