package com.studymate.domain.testingmodule;

import lombok.Builder;

import java.util.List;

@Builder
public record Exam(
        String examId,
        String examName,
        List<Question> questions

) {
}
