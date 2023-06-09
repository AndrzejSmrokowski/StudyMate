package com.studymate.infrastructure.security.jwt;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JwtBlacklistRepository {

    private final Set<String> blacklist = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void add(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
