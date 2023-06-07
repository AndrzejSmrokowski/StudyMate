package com.studymate;

public interface SampleEducationalMaterialResponse {
    default String bodyWithZeroEducationalMaterialsJson() {
        return "[]";
    }
}
