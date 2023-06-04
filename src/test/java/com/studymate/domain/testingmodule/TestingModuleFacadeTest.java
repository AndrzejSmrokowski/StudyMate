package com.studymate.domain.testingmodule;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class TestingModuleFacadeTest {

    private final TestingModuleFacade testingModuleFacade = new TestingModuleFacade(new TestService(new InMemoryTestRepository(), new InMemoryTestResultRepository()), new TestSolver());
    private final TestDataProvider dataProvider = new TestDataProvider();

    @Test
    void shouldCreateAndGetTestById() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        Exam retrivedExam = testingModuleFacade.getTestById(exam.examId());
        // then
        assertThat(retrivedExam).isEqualTo(Exam.builder()
                .examId(retrivedExam.examId())
                .examName(exam.examName())
                .questions(exam.questions())
                .build());

    }

    @Test
    void shouldSolveTestWhenThereAreCorrectAnswersAndSendResults() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        TestResult testResult = testingModuleFacade.solveTest(exam.examId(), dataProvider.testSubmissionDataForTestWithCorrectAnswers(exam.examId()));
        // then
        assertThat(testResult.score()).isEqualTo(100);

    }
    @Test
    void shouldSolveTestWhenThereAreIncorrectAnswersAndSendResults() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        // when
        TestResult testResult = testingModuleFacade.solveTest(exam.examId(), dataProvider.testSubmissionDataForTestWithIncorrectAnswers(exam.examId()));
        // then
        assertThat(testResult.score()).isEqualTo(0);

    }

    @Test
    void shouldReturnListOfTestResultsByTestId() {
        // given
        Exam exam = testingModuleFacade.createTest(dataProvider.testDataForTest());
        testingModuleFacade.solveTest(exam.examId(), dataProvider.testSubmissionDataForTestWithIncorrectAnswers(exam.examId()));
        // when
        List<TestResult> resultList = testingModuleFacade.getTestResults(exam.examId());
        // then
        assertThat(resultList).hasSize(1);
    }


}