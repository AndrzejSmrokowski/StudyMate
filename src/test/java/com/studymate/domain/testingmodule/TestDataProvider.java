package com.studymate.domain.testingmodule;

import com.studymate.domain.testingmodule.dto.AnswerData;
import com.studymate.domain.testingmodule.dto.QuestionData;
import com.studymate.domain.testingmodule.dto.TestData;
import com.studymate.domain.testingmodule.dto.TestSubmissionData;

import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {

    private List<QuestionData> questions;
    private List<AnswerData> answers;
    public TestData testDataForTest() {
        initializeQuestionsForPhysicsTest();
        return new TestData("Test z Fizyki", questions);
    }
    private void initializeQuestionsForPhysicsTest() {
        questions = new ArrayList<>();

        QuestionData question1 = new QuestionData("id1","Pytanie 1", List.of("Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D"), AnswerChoice.A);
        QuestionData question2 = new QuestionData("id2","Pytanie 2", List.of("Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D"), AnswerChoice.C);
        QuestionData question3 = new QuestionData("id3","Pytanie 3", List.of("Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D"), AnswerChoice.D);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
    }

    public TestSubmissionData testSubmissionDataForTestWithCorrectAnswers(String testId) {
        initializeCorrectAnswers();
        return new TestSubmissionData(testId, "userId", answers);
    }

    private void initializeCorrectAnswers() {
        answers = new ArrayList<>();

        AnswerData answerData1 = new AnswerData("id1",AnswerChoice.A);
        AnswerData answerData2 = new AnswerData("id2",AnswerChoice.C);
        AnswerData answerData3 = new AnswerData("id3",AnswerChoice.D);

        answers.add(answerData1);
        answers.add(answerData2);
        answers.add(answerData3);
    }

    public TestSubmissionData testSubmissionDataForTestWithIncorrectAnswers(String testId) {
        initializeIncorrectAnswers();
        return new TestSubmissionData(testId, "userId", answers);
    }

    private void initializeIncorrectAnswers() {
        answers = new ArrayList<>();

        AnswerData answerData1 = new AnswerData("id1",AnswerChoice.D);
        AnswerData answerData2 = new AnswerData("id2",AnswerChoice.A);
        AnswerData answerData3 = new AnswerData("id3",AnswerChoice.C);

        answers.add(answerData1);
        answers.add(answerData2);
        answers.add(answerData3);
    }
}
