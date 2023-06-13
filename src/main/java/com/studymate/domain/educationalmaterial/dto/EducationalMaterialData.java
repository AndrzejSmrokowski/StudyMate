package com.studymate.domain.educationalmaterial.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record EducationalMaterialData(
        @NotNull(message = "{title.not.null}")
        @NotEmpty(message = "{title.not.empty}")
        String title,
        @NotNull(message = "{description.not.null}")
        @NotEmpty(message = "{description.not.empty}")
        String description,
        @NotNull(message = "{content.not.null}")
        @NotEmpty(message = "{content.not.empty}")
        String content
) {
}

