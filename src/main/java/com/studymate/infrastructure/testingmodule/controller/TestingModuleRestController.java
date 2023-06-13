package com.studymate.infrastructure.testingmodule.controller;

import com.studymate.domain.progresstracking.ProgressTrackingFacade;
import com.studymate.domain.testingmodule.Exam;
import com.studymate.domain.testingmodule.Question;
import com.studymate.domain.testingmodule.TestResult;
import com.studymate.domain.testingmodule.TestingModuleFacade;
import com.studymate.domain.testingmodule.dto.TestData;
import com.studymate.domain.testingmodule.dto.TestSubmissionData;
import com.studymate.domain.user.User;
import com.studymate.domain.user.UserManagementFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
public class TestingModuleRestController {
    private final TestingModuleFacade testingModuleFacade;
    private final UserManagementFacade userManagementFacade;
    private final ProgressTrackingFacade progressTrackingFacade;


    @PostMapping
    public ResponseEntity<Exam> createTest(@RequestBody @Valid TestData testData) {
        Exam exam = testingModuleFacade.createTest(testData);
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getTestById(@PathVariable String id) {
        Exam exam = testingModuleFacade.getTestById(id);
        return ResponseEntity.ok(exam);
    }
    @PostMapping ("/{testId}/submit")
    public ResponseEntity<TestResult> solveTest(@PathVariable String testId, @RequestBody @Valid TestSubmissionData submissionData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userManagementFacade.findUserByUsername(currentUserName);
        String currentUserId = currentUser.userId();
        TestSubmissionData submissionDataWithUserId = TestSubmissionData.builder()
                .userId(currentUserId)
                .testId(submissionData.testId())
                .answers(submissionData.answers())
                .build();
        TestResult testResult = testingModuleFacade.solveTest(testId, submissionDataWithUserId);
        progressTrackingFacade.addTestScoreToProgress(testResult);
        return ResponseEntity.ok(testResult);
    }

    @GetMapping("/{testId}/results")
    public ResponseEntity<List<TestResult>> getTestResults(@PathVariable String testId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userManagementFacade.findUserByUsername(currentUserName);
        String currentUserId = currentUser.userId();

        List<TestResult> testResults = testingModuleFacade.getTestResults(testId, currentUserId);
        return ResponseEntity.ok(testResults);

    }

    @DeleteMapping("/{testId}")
    public ResponseEntity<Void> deleteTest(@PathVariable String testId) {
        testingModuleFacade.deleteTest(testId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{testId}/questions")
    public ResponseEntity<List<Question>> getTestQuestions(@PathVariable String testId) {
        List<Question> questions = testingModuleFacade.getTestQuestions(testId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{testId}/questions/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable String testId, @PathVariable String questionId) {
        Question question = testingModuleFacade.getQuestionById(testId, questionId);
        return question != null
                ? ResponseEntity.ok(question)
                : ResponseEntity.notFound().build();
    }

}
