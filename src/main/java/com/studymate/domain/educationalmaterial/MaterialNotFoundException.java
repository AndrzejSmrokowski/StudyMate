package com.studymate.domain.educationalmaterial;

public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(String message) {
        super(message);
    }
}
