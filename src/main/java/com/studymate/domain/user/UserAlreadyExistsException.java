package com.studymate.domain.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("User with the given username already exists: " + username);
    }
}
