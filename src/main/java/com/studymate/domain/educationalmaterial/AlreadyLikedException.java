package com.studymate.domain.educationalmaterial;

public class AlreadyLikedException extends RuntimeException {
    public AlreadyLikedException(String username) {
        super(String.format("User with username: %s already liked EducationalMaterial", username));
    }
}
