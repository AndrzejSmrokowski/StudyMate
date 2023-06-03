package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.QuestionData;

import java.util.ArrayList;
import java.util.List;

public class QuestionMapper {
    public static List<Question> mapToQuestions(List<QuestionData> questions) {
        List<Question> result = new ArrayList<>();
        for (QuestionData questionData :
                questions) {
            result.add(mapToQuestion(questionData));
        }
        return result;

    }

    private static Question mapToQuestion(QuestionData questionData) {
        return Question.builder()
                .questionId(questionData.questionId())
                .questionText(questionData.questionText())
                .options(questionData.options())
                .correctAnswer(questionData.correctAnswer())
                .build();
    }
}
