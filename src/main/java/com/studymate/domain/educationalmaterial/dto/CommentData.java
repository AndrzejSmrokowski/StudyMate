package com.studymate.domain.educationalmaterial.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CommentData(
        @NotNull(message = "{message.not.null}")
        @NotEmpty(message = "{message.not.empty}")
        String text,
        String author
) {
}
