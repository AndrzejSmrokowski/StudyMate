package com.studymate.domain.user;

public class UserNotFoundException extends  RuntimeException {
    public UserNotFoundException(String username) {
        super("User with the given username not found: " + username);
    }}


