package com.studymate.domain.testingmodule.dto;


import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Builder
public record TestSubmissionData(
        @NotNull(message = "{testid.not.null}")
        @NotEmpty(message = "{testid.not.empty}")
        String testId,
        String userId,
        @NotNull(message = "{answers.not.null}")
        List<AnswerData> answers
) {
}
