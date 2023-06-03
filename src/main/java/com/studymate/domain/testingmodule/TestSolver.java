package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.AnswerData;
import com.studymate.domain.testingmodule.dto.TestSubmissionData;

import java.util.List;

public class TestSolver {
    public TestResult calculateResult(Exam exam, TestSubmissionData submissionData) {
        List<Question> questionList = exam.questions();
        List<AnswerData> answers = submissionData.answers();

        int totalQuestions = questionList.size();
        int correctAnswers = 0;

        for (Question question : questionList) {
            String questionId = question.questionId();
            AnswerData answer = findAnswerById(answers, questionId);

            if (answer != null && question.correctAnswer().equals(answer.answerChoice())) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / totalQuestions * 100;

        return new TestResult(score, exam.examId(), submissionData.userId());
    }

    private AnswerData findAnswerById(List<AnswerData> answers, String questionId) {
        for (AnswerData answer : answers) {
            if (answer.questionId().equals(questionId)) {
                return answer;
            }
        }
        return null;
    }
}