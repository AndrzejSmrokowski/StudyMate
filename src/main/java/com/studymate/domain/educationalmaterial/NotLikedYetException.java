package com.studymate.domain.educationalmaterial;

public class NotLikedYetException extends RuntimeException {
    public NotLikedYetException(String username) {
        super(String.format("User with username: %s didn't liked EducationalMaterial yet", username));
    }
}
