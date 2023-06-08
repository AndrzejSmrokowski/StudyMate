package com.studymate.infrastructure.testingmodule.controller;

import com.studymate.domain.testingmodule.Exam;
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

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
public class TestingModuleRestController {
    private final TestingModuleFacade testingModuleFacade;
    private final UserManagementFacade userManagementFacade;


    @PostMapping
    public ResponseEntity<Exam> createTest(@RequestBody TestData testData) {
        Exam exam = testingModuleFacade.createTest(testData);
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getTestById(@PathVariable String id) {
        Exam exam = testingModuleFacade.getTestById(id);
        return ResponseEntity.ok(exam);
    }
    @PostMapping ("/{testId}/submit")
    public ResponseEntity<TestResult> solveTest(@PathVariable String testId, @RequestBody TestSubmissionData submissionData) {
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
}
