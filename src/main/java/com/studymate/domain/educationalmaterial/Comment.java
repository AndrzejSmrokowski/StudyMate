package com.studymate.domain.educationalmaterial;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record Comment(
        String text,
        String author
) implements Serializable {
}
