package com.studymate.domain.educationalmaterial.dto;

import lombok.Builder;

@Builder
public record EducationalMaterialData(String title, String description, String content) {
}

