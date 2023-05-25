package com.studymate.domain.educationalmaterial;

import lombok.Builder;

@Builder
record EducationalMaterial(
        String id,
        String title,
        String description,
        String content
) {

}
