package com.studymate.domain.learningreminder;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryReminderRepository implements ReminderRepository {

    Map<String, Reminder> database = new ConcurrentHashMap<>();

    @Override
    public List<Reminder> findAllByUserId(String userId) {
        return database.values().stream()
                .filter(reminder -> reminder.userId().equals(userId))
                .collect(Collectors.toList());
    }


    @Override
    public List<Reminder> findDueReminders(LocalDateTime now) {
        return database.values().stream()
                .filter(reminder -> !reminder.reminderTime().isAfter(now))
                .collect(Collectors.toList());
    }


    @Override
    public <S extends Reminder> S save(S entity) {
        String id = UUID.randomUUID().toString();
        Reminder reminder = Reminder.builder()
                .message(entity.message())
                .sent(entity.sent())
                .reminderTime(entity.reminderTime())
                .id(id)
                .userId(entity.userId())
                .build();
        database.put(id, reminder);

        return (S) reminder;
    }

    @Override
    public <S extends Reminder> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Reminder> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Reminder> findAll() {
        return null;
    }

    @Override
    public Iterable<Reminder> findAllById(Iterable<String> strings) {
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
    public void delete(Reminder entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Reminder> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Reminder> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Reminder> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Reminder> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Reminder> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Reminder> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Reminder> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Reminder> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Reminder> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Reminder> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Reminder> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Reminder, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
