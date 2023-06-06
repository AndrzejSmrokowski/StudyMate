package com.studymate.domain.progresstracking;

public class MaterialAlreadyReviewedException extends RuntimeException {
    public MaterialAlreadyReviewedException(String id) {
        super("Material with this id already Reviewed: " + id);
    }
}
