package com.studymate.domain.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super(String.format("User with id: %s not found", userId));
    }
}
