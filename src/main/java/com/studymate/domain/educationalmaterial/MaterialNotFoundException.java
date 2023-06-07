package com.studymate.domain.educationalmaterial;

public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(String id) {
        super(String.format("Content with id %s not found", id));
    }
}
