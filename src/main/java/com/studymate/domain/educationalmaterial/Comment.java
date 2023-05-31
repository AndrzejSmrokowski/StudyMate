package com.studymate.domain.educationalmaterial;

import lombok.Builder;

@Builder
public record Comment(
        String text,
        String author
) {
}
