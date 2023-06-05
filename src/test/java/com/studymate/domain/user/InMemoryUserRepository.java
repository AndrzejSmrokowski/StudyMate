package com.studymate.domain.user;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    Map<String, User> database = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(database.get(username));
    }

    @Override
    public User save(User user) {
        String id = UUID.randomUUID().toString();

        User registeredUser = User.builder()
                .userId(id)
                .username(user.username())
                .password(user.password())
                .build();
        database.put(user.username(), registeredUser);
        return registeredUser;
    }
}
