package com.studymate.domain.testingmodule;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;


class TestingModuleFacadeTest {

    private final TestingModuleFacade testingModuleFacade = new TestingModuleFacade(new TestService(new InMemoryTestRepository(), new InMemoryTestResultRepository()), new TestSolver());
    private final TestDataProvider dataProvider = new TestDataProvider();

    @Test
    void shouldCreateAndGetTestById() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        Exam retrivedExam = testingModuleFacade.getTestById(exam.id());
        // then
        assertThat(retrivedExam).isEqualTo(Exam.builder()
                .id(retrivedExam.id())
                .examName(exam.examName())
                .questions(exam.questions())
                .build());

    }

    @Test
    void shouldSolveTestWhenThereAreCorrectAnswersAndSendResults() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        TestResult testResult = testingModuleFacade.solveTest(exam.id(), dataProvider.testSubmissionDataForTestWithCorrectAnswers(exam.id()));
        // then
        assertThat(testResult.score()).isEqualTo(100);

    }
    @Test
    void shouldSolveTestWhenThereAreIncorrectAnswersAndSendResults() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        TestResult testResult = testingModuleFacade.solveTest(exam.id(), dataProvider.testSubmissionDataForTestWithIncorrectAnswers(exam.id()));
        // then
        assertThat(testResult.score()).isEqualTo(0);

    }

    @Test
    void shouldReturnListOfTestResultsByTestId() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        testingModuleFacade.solveTest(exam.id(), dataProvider.testSubmissionDataForTestWithIncorrectAnswers(exam.id()));
        // when
        List<TestResult> resultList = testingModuleFacade.getTestResults(exam.id(), "userId");
        // then
        assertThat(resultList).hasSize(1);
    }

    @Test
    void shouldReturnListOfQuestionsByTestId() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        List<Question> questions = testingModuleFacade.getTestQuestions(exam.id());
        // then
        assertThat(questions).hasSize(3);

    }

    @Test
    void shouldReturnQuestionById() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        String testId = exam.id();
        List<Question> questions = testingModuleFacade.getTestQuestions(testId);
        String questionId = questions.get(0).questionId();

        // when
        Question question = testingModuleFacade.getQuestionById(testId, questionId);

        // then
        assertAll(() -> assertThat(question).isNotNull(),
                  () -> assertThat(question.questionId()).isEqualTo("id1"));
    }

    @Test
    void shouldThrowTestNotFoundExceptionWhenTestNotFound() {
        // given
        String testId = "testerinio1";

        //when
        Throwable thrown = catchThrowable(() -> testingModuleFacade.getTestById(testId));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(TestNotFoundException.class)
                .hasMessage("Test not found with ID: " + testId);
        
    }
    @Test
    void shouldThrowResultsNotFoundExceptionWhenThereAreNoResults() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());

        //when
        Throwable thrown = catchThrowable(() -> testingModuleFacade.getTestResults(exam.id(), "username"));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(ResultsNotFoundException.class)
                .hasMessage("Result not found for test id: " + exam.id());

    }

}