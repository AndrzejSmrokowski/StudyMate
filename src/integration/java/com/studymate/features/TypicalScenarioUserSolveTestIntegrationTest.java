package com.studymate.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.studymate.BaseIntegrationTest;
import com.studymate.DataProviderForIntegrationTests;
import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.MaterialStatus;
import com.studymate.domain.progresstracking.Progress;
import com.studymate.domain.testingmodule.Exam;
import com.studymate.domain.testingmodule.TestResult;
import com.studymate.domain.user.User;
import com.studymate.domain.user.dto.LogoutResponseDto;
import com.studymate.domain.user.dto.RegistrationResultDto;
import com.studymate.domain.user.dto.UserData;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("redis-test")
public class TypicalScenarioUserSolveTestIntegrationTest extends BaseIntegrationTest implements DataProviderForIntegrationTests {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void userLearnFromEducationalMaterialThenSolvesTestAndWantsToSeeHisProgress() throws Exception {
        //Step 1: User sends a POST request to /api/users/register with registration data such as username, password, and the system registers a new user.
        // given & when
        ResultActions registerAction = mockMvc.perform(post("/api/users/register")
                .content("""
                        {
                        "username": "username",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult registerActionResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerActionResultJson = registerActionResult.getResponse().getContentAsString();
        RegistrationResultDto registrationResultDto = objectMapper.readValue(registerActionResultJson, RegistrationResultDto.class);
        assertAll(
                () -> assertThat(registrationResultDto.username()).isEqualTo("username"),
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.id()).isNotNull()
        );

        //Step 2: User sends a POST request to /api/users/login with login credentials (i.e., username or email, password), and the system authenticates the user and returns a JWT token.
        // given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/api/users/login")
                .content("""
                        {
                        "username": "username",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult mvcResult = successLoginRequest.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(json, JwtResponseDto.class);
        String token = jwtResponse.token();
        assertAll(
                () -> assertThat(jwtResponse.username()).isEqualTo("username"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );

        //Step 3: User sends a GET request to /api/users/{userId}, where {userId} is the user identifier, and the system returns the respective user data.
        // given & when
        ResultActions performGetUserById = mockMvc.perform(get("/api/users/" + registrationResultDto.id())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //then
        MvcResult getUserByIdMvcResult = performGetUserById.andExpect(status().isOk()).andReturn();
        String jsonWithUserById = getUserByIdMvcResult.getResponse().getContentAsString();
        UserData userData = objectMapper.readValue(jsonWithUserById, new TypeReference<>() {
        });

        assertAll(
                () -> assertThat(userData.username()).isEqualTo("username"),
                () -> assertThat(userData.progress()).isNotNull(),
                () -> assertThat(userData.authorities()).hasSize(1)
        );

        //Step 4: User sends a GET request to /api/educational-content, and the system returns a list of all available educational materials.
        // given
        String educationalContentUrl = "/api/educational-content";
        // when
        ResultActions perform = mockMvc.perform(get(educationalContentUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult2 = perform.andExpect(status().isOk()).andReturn();
        String jsonWithEducationalMaterials = mvcResult2.getResponse().getContentAsString();
        List<EducationalMaterial> educationalMaterials = objectMapper.readValue(jsonWithEducationalMaterials, new TypeReference<>() {
        });

        assertThat(educationalMaterials).isEmpty();

        //Step 5: User sends a POST request to /api/educational-content with the data of a new educational material (e.g., title, description, content), and the system creates a new educational material.
        // given & when
        ResultActions performPostEducationalContent = mockMvc.perform(post(educationalContentUrl)
                .header("Authorization", "Bearer " + token)
                .content(bodyWithEducationalMaterialDataJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult PostEducationalContentMvcResult = performPostEducationalContent.andExpect(status().isCreated()).andReturn();

        // Extract the Location header which contains the URI of the created resource
        String locationHeader = PostEducationalContentMvcResult.getResponse().getHeader("Location");

        // Send a GET request to the URI of the created resource to retrieve it
        MvcResult getCreatedResourceMvcResult = mockMvc.perform(get(locationHeader)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk()).andReturn();

        String jsonWithEducationalMaterial = getCreatedResourceMvcResult.getResponse().getContentAsString();
        EducationalMaterial educationalMaterial = objectMapper.readValue(jsonWithEducationalMaterial, new TypeReference<>() {
        });

        assertAll(
                () -> assertThat(educationalMaterial.status()).isEqualTo(MaterialStatus.PENDING),
                () -> assertThat(educationalMaterial.description()).isEqualTo("Krotkie wprowadzenie do podstaw fizyki kwantowej"),
                () -> assertThat(educationalMaterial.comments()).isNotNull()
        );


        //Step 6: User sends a PUT request to /api/educational-content/{contentId}, where {contentId} is the identifier of the existing content, along with the updated data (e.g., title, description, content), and the system updates the educational content.
        // given & when
        ResultActions performPutEducationalContent = mockMvc.perform(put(educationalContentUrl + "/" + educationalMaterial.id())
                .header("Authorization", "Bearer " + token)
                .content(bodyWithUpdatedEducationalMaterialDataJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult putEducationalContentMvcResult = performPutEducationalContent.andExpect(status().isOk()).andReturn();
        String jsonWithUpdatedMaterial = putEducationalContentMvcResult.getResponse().getContentAsString();
        EducationalMaterial updatedEducationalMaterial = objectMapper.readValue(jsonWithUpdatedMaterial, new TypeReference<>() {
        });

        assertAll(
                () -> assertThat(updatedEducationalMaterial.status()).isEqualTo(MaterialStatus.PENDING),
                () -> assertThat(updatedEducationalMaterial.description()).isEqualTo("Krotkie wprowadzenie do podstaw fizyki klasyczenj")
        );

        //Step 7: User sends a POST request to /api/educational-content/{contentId}/review  to review educational material
        // given & when
        ResultActions performReviewEducationalContent = mockMvc.perform(post(educationalContentUrl + "/" + educationalMaterial.id() + "/review")
                .header("Authorization", "Bearer " + token)
                .content(bodyWithUpdatedEducationalMaterialDataJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        performReviewEducationalContent.andExpect(status().isOk()).andReturn();

        //Step 8: User sends a POST request to /api/tests to create a new test, providing test data (e.g., title, questions, answers), and the system creates a new test.
        // given
        String testingModuleUrl = "/api/tests";

        // when
        ResultActions performPostTests = mockMvc.perform(post(testingModuleUrl)
                .header("Authorization", "Bearer " + token)
                .content(bodyWithTestDataJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult postTestMvcResult = performPostTests.andExpect(status().isOk()).andReturn();
        String jsonWithCreatedTest = postTestMvcResult.getResponse().getContentAsString();
        Exam exam = objectMapper.readValue(jsonWithCreatedTest, new TypeReference<>() {
        });

        assertAll(
                () -> assertThat(exam.questions()).isEqualTo(provideSampleExam().questions()),
                () -> assertThat(exam.examName()).isEqualTo(provideSampleExam().examName())
        );

        //Step 9: User sends a GET request to /api/tests/{testId}, where {testId} is the identifier of the existing test, and the system returns the details of that test.
        // given
        String examId = exam.id();

        // when
        ResultActions performGetTests = mockMvc.perform(get(testingModuleUrl + "/" + examId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult getTestMvcResult = performGetTests.andExpect(status().isOk()).andReturn();
        String jsonWithTest = getTestMvcResult.getResponse().getContentAsString();
        Exam testById = objectMapper.readValue(jsonWithTest, new TypeReference<>() {
        });
        assertAll(
                () -> assertThat(testById.questions()).isEqualTo(provideSampleExam().questions()),
                () -> assertThat(testById.examName()).isEqualTo(provideSampleExam().examName())
        );

        //Step 10: User sends a POST request to /api/tests/{testId}/submit, where {testId} is the test identifier, along with answers to the test questions, and the system evaluates the user's responses and returns the results.
        // given & when
        ResultActions performPostTestSubmission = mockMvc.perform(post(testingModuleUrl + "/" + examId + "/submit")
                .header("Authorization", "Bearer " + token)
                .content(bodyWithTestSubmissionDataJson(examId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult postTestSubmissionMvcResult = performPostTestSubmission.andExpect(status().isOk()).andReturn();
        String jsonWithTestResult = postTestSubmissionMvcResult.getResponse().getContentAsString();
        TestResult testResult = objectMapper.readValue(jsonWithTestResult, new TypeReference<>() {
        });

        assertAll(
                () -> assertThat(testResult.testId()).isEqualTo(examId),
                () -> assertThat(testResult.score()).isEqualTo(100),
                () -> assertThat(testResult.userId()).isEqualTo(registrationResultDto.id())
        );

        //Step 11: User sends a GET request to /api/tests/{testId}/results, where {testId} is the test identifier, and the system returns the results of that test.
        // given & when
        ResultActions performGetTestResult = mockMvc.perform(get(testingModuleUrl + "/" + examId + "/results")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult getTestResultMvcResult = performGetTestResult.andExpect(status().isOk()).andReturn();
        String jsonWithTestResultById = getTestResultMvcResult.getResponse().getContentAsString();
        List<TestResult> testResultsByTestId = objectMapper.readValue(jsonWithTestResultById, new TypeReference<>() {
        });
        TestResult testResultByTestId = testResultsByTestId.get(0);
        assertAll(
                () -> assertThat(testResultByTestId.testId()).isEqualTo(examId),
                () -> assertThat(testResultByTestId.score()).isEqualTo(100),
                () -> assertThat(testResultByTestId.userId()).isEqualTo(registrationResultDto.id())
        );

        //Step 12: User sends a DELETE request to /api/tests/{testId}, where {testId} is the test identifier, and the system deletes that test.
        // given & when
        ResultActions performDeleteTest = mockMvc.perform(delete(testingModuleUrl + "/" + examId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        performDeleteTest.andExpect(status().isNoContent()).andReturn();

        //Step 13: User sends a DELETE request to /api/educational-content/{contentId}, where {contentId} is the identifier of the educational content, and the system deletes that educational content.
        // given & then
        ResultActions performDeleteEducationalContent = mockMvc.perform(delete(educationalContentUrl + "/" + educationalMaterial.id())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        performDeleteEducationalContent.andExpect(status().isNoContent()).andReturn();

        //Step 14: User sends a GET request to /api/progress and system returns progress for that user
        // given
        String progressTrackingUrl = "/api/progress";

        // when
        ResultActions performGetProgress = mockMvc.perform(get(progressTrackingUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then 
        MvcResult getProgressMvcResult = performGetProgress.andExpect(status().isOk()).andReturn();
        String jsonWithProgress = getProgressMvcResult.getResponse().getContentAsString();
        Progress progress = objectMapper.readValue(jsonWithProgress, Progress.class);

        assertAll(
                () -> assertThat(progress.userId()).isEqualTo(registrationResultDto.id()),
                () -> assertThat(progress.testScores()).hasSize(1),
                () -> assertThat(progress.reviewedMaterialsId()).hasSize(1),
                () -> assertThat(progress.reviewedMaterialsId().get(0)).isEqualTo(educationalMaterial.id())
        );

        //Step 15: User sends a POST request to /api/users/logout, and the system logs the user out.
        // given & when
        ResultActions performLogoutRequest = mockMvc.perform(post("/api/users/logout")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult logoutMvcResult = performLogoutRequest.andExpect(status().isOk()).andReturn();
        String jsonWithLogoutResponse = logoutMvcResult.getResponse().getContentAsString();
        LogoutResponseDto logoutResponseDto = objectMapper.readValue(jsonWithLogoutResponse, LogoutResponseDto.class);

        assertAll(
                () -> assertThat(logoutResponseDto.username()).isEqualTo("username"),
                () -> assertThat(logoutResponseDto.loggedOut()).isTrue()
        );

        // further verifying the user is logged out by trying to access a secured endpoint
        ResultActions performSecuredEndpointRequest = mockMvc.perform(get(educationalContentUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        performSecuredEndpointRequest.andExpect(status().isUnauthorized());

        //Step 16: Admin sends a POST request to /apo/users/login with login credentials (i.e., username or email, password), and the system authenticates the admin and returns a JWT token.
        // given & when
        ResultActions adminLoginRequest = mockMvc.perform(post("/api/users/login")
                .content("""
                        {
                        "username": "admin",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult adminLoginMvcResult = adminLoginRequest.andExpect(status().isOk()).andReturn();
        String adminLoginJson = adminLoginMvcResult.getResponse().getContentAsString();
        JwtResponseDto jwtAdminResponse = objectMapper.readValue(adminLoginJson, JwtResponseDto.class);
        String adminToken = jwtAdminResponse.token();

        assertAll(
                () -> assertThat(jwtAdminResponse.username()).isEqualTo("admin"),
                () -> assertThat(adminToken).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );


        //Step 17: Admin sends a GET request to /api/users, and the system returns a list of all users in the system.
        // given
        String usersUrl = "/api/users";

        // when
        ResultActions performGetUsers = mockMvc.perform(get(usersUrl)
                .header("Authorization", "Bearer " + adminToken) // Assuming 'adminToken' has been initialized earlier
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult getUsersMvcResult = performGetUsers.andExpect(status().isOk()).andReturn();
        String jsonWithUsers = getUsersMvcResult.getResponse().getContentAsString();
        List<User> allUsers = objectMapper.readValue(jsonWithUsers, new TypeReference<>() {}
        );

        assertAll(
                () -> assertThat(allUsers).isNotEmpty(),
                () -> assertThat(allUsers).anyMatch(user -> user.username().equals("username"))
        );

        //Step 18: Admin sends a DELETE request to /api/users/{userId}, where {userId} is the user identifier, and the system deletes that user from the system.
        // given
        String userId = registrationResultDto.id();

        // when
        ResultActions performDeleteUser = mockMvc.perform(delete(usersUrl + "/" + userId)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        performDeleteUser.andExpect(status().isNoContent()).andReturn();

        // further verifying the user has been deleted by trying to get the user details
        ResultActions performGetUser = mockMvc.perform(get(usersUrl + "/" + userId)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        performGetUser.andExpect(status().isNotFound());


    }
}
