package com.studymate.domain.testingmodule.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record TestData(
        @NotNull(message = "{name.not.null}")
        @NotEmpty(message = "{name.not.empty}")
        String testName,
        @NotNull(message = "{questions.not.null}")

        List<QuestionData> questions

) {
}
