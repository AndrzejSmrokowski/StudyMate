package com.studymate.features;

import com.studymate.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

public class TypicalScenarioUserSolveTestIntegrationTest extends BaseIntegrationTest {
    @Test
    public void userLearnFromEducationalMaterialThenSolvesTestAndWantsToSeeHisProgress() {
//Step 1: User sends a POST request to /api/users/register with registration data such as username, email address, password, and the system registers a new user.

//Step 2: User sends a POST request to /api/users/login with login credentials (i.e., username or email, password), and the system authenticates the user and returns a JWT token.
//Step 3: User sends a GET request to /api/users/{userId}, where {userId} is the user identifier, and the system returns the respective user data.
//Step 4: User sends a GET request to /api/educational-content, and the system returns a list of all available educational materials.
//Step 5: User sends a POST request to /api/educational-content with the data of a new educational material (e.g., title, description, content), and the system creates a new educational material.
//Step 6: User sends a PUT request to /api/educational-content/{contentId}, where {contentId} is the identifier of the existing content, along with the updated data (e.g., title, description, content), and the system updates the educational content.
//Step 7: User sends a POST request to /api/tests to create a new test, providing test data (e.g., title, questions, answers), and the system creates a new test.
//Step 8: User sends a GET request to /api/tests/{testId}, where {testId} is the identifier of the existing test, and the system returns the details of that test.
//Step 9: User sends a POST request to /api/tests/{testId}/submit, where {testId} is the test identifier, along with answers to the test questions, and the system evaluates the user's responses and returns the results.
//Step 10: User sends a GET request to /api/tests/{testId}/results, where {testId} is the test identifier, and the system returns the results of that test.
//Step 11: User sends a DELETE request to /api/tests/{testId}, where {testId} is the test identifier, and the system deletes that test.
//Step 12: User sends a DELETE request to /api/educational-content/{contentId}, where {contentId} is the identifier of the educational content, and the system deletes that educational content.
//Step 13: User sends a POST request to /api/users/logout, and the system logs the user out.
//Step 14: Admin sends a GET request to /api/users, and the system returns a list of all users in the system.
//Step 15: Admin sends a DELETE request to /api/users/{userId}, where {userId} is the user identifier, and the system deletes that user from the system.
//Step 16: User sends a GET request to /api/educational-content/{contentId}, where {contentId} is the identifier of the educational content, and the system returns the details of that educational content.
//Step 17: User sends a GET request to /api/educational-content/{contentId}/comments, where {contentId} is the identifier of the educational content, and the system returns a list of comments for that educational content.
//Step 18: User sends a POST request to /api/educational-content/{contentId}/comments, where {contentId} is the identifier of the educational content, along with the content of the comment, and the system adds a new comment to that educational content.
//Step 19: User sends a GET request to /api/tests, and the system returns a list of all available tests.
//Step 20: User sends a GET request to /api/tests/{testId}/questions, where {testId} is the test identifier, and the system returns a list of questions for that test.
//Step 21: User sends a GET request to /api/tests/{testId}/questions/{questionId}, where {testId} is the test identifier and {questionId} is the question identifier, and the system returns the details of that question.
//Step 22: User sends a POST request to /api/tests/{testId}/questions/{questionId}/answer, where {testId} is the test identifier and {questionId} is the question identifier, along with the answer to that question, and the system saves the user's answer.
//Step 23: User sends a DELETE request to /api/tests/{testId}/questions/{questionId}/answer, where {testId} is the test identifier and {questionId} is the question identifier, and the system deletes the user's answer to that question.
//Step 24: Admin sends a POST request to /api/educational-content/{contentId}/approve, where {contentId} is the identifier of the educational content, and the system approves that educational content.
//Step 25: Admin sends a POST request to /api/educational-content/{contentId}/reject, where {contentId} is the identifier of the educational content, and the system rejects that educational content.
//Step 26: User sends a POST request to /api/educational-content/{contentId}/like, where {contentId} is the identifier of the educational content, and the system adds a like to that educational content.
//Step 27: User sends a POST request to /api/educational-content/{contentId}/unlike, where {contentId} is the identifier of the educational content, and the system removes a like from that educational content.
    }
}
