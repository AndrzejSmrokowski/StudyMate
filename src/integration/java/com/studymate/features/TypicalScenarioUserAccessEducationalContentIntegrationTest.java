package com.studymate.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.studymate.BaseIntegrationTest;
import com.studymate.DataProviderForIntegrationTests;
import com.studymate.domain.educationalmaterial.Comment;
import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.EducationalMaterialFacade;
import com.studymate.domain.user.dto.RegistrationResultDto;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TypicalScenarioUserAccessEducationalContentIntegrationTest extends BaseIntegrationTest implements DataProviderForIntegrationTests {
    @Autowired
    EducationalMaterialFacade educationalMaterialFacade;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void userWantsToSeeEducationalContentButHasToBeLoggedInAndExternalServerShouldHaveSomeContent() throws Exception {
        // step 1: there is no educational content in the database
        // step 2: user tries to retrieve JWT token by making a POST request to /api/users/login with username=someUser, password=somePassword and system returns UNAUTHORIZED(401)
        // given & when
        ResultActions failedLoginRequest = mockMvc.perform(post("/api/users/login")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        failedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                          "message": "Bad Credentials",
                          "status": "UNAUTHORIZED"
                        }
                        """.trim()));
        // step 3: user makes a GET request to /api/educational-content without JWT token and system returns UNAUTHORIZED(401)
        // given & when
        ResultActions failedGetEducationalMaterialsRequest = mockMvc.perform(get("/api/educational-content")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedGetEducationalMaterialsRequest.andExpect(status().isForbidden());

        // step 4: user makes a POST request to /api/register with username=someUser, password=somePassword and system registers user and returns OK(200)
        // given & when
        ResultActions registerAction = mockMvc.perform(post("/api/users/register")
                .content("""
                        {
                        "username": "someUser",
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
                () -> assertThat(registrationResultDto.username()).isEqualTo("someUser"),
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.id()).isNotNull()
        );

        // step 5: user retrieves JWT token by making a POST request to /api/users/login with username=someUser, password=somePassword and system returns OK(200) and jwttoken=AAAA.BBBB.CCC
        // given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/api/users/login")
                .content("""
                        {
                        "username": "someUser",
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
                () -> assertThat(jwtResponse.username()).isEqualTo("someUser"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );

        // step 6: user makes a GET request to /educational-content with header “Authorization: Bearer AAAA.BBBB.CCC” and system returns OK(200) with 0 educational contents
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

        // step 7: there are 2 new educational contents in the system
        educationalMaterialFacade.createEducationalMaterial(getExpectedMaterialAboutQuantumPhysics());
        educationalMaterialFacade.createEducationalMaterial(getMaterialAboutBiology());

        // step 8: user makes a GET request to /educational-content with header “Authorization: Bearer AAAA.BBBB.CCC” and system returns OK(200) with 2 educational contents with ids: 1 and 2
        // given & when
        ResultActions performGetForTwoMaterials = mockMvc.perform(get(educationalContentUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        MvcResult performGetForTwoMaterialsMvcResult = performGetForTwoMaterials.andExpect(status().isOk()).andReturn();
        String jsonWithTwoMaterials = performGetForTwoMaterialsMvcResult.getResponse().getContentAsString();
        List<EducationalMaterial> twoMaterials = objectMapper.readValue(jsonWithTwoMaterials, new TypeReference<>() {
        });
        assertThat(twoMaterials).hasSize(2);
        EducationalMaterial expectedFirstMaterial = getMaterialAboutBiology();
        EducationalMaterial expectedSecondMaterial = getExpectedMaterialAboutQuantumPhysics();

        assertThat(twoMaterials).containsExactlyInAnyOrder(
                new EducationalMaterial(expectedFirstMaterial.id(), expectedFirstMaterial.title(), expectedFirstMaterial.description(), expectedFirstMaterial.content(), expectedFirstMaterial.comments(), expectedFirstMaterial.status(), expectedFirstMaterial.likes(), expectedFirstMaterial.likedBy()),
                new EducationalMaterial(expectedSecondMaterial.id(), expectedSecondMaterial.title(), expectedSecondMaterial.description(), expectedSecondMaterial.content(), expectedSecondMaterial.comments(), expectedSecondMaterial.status(), expectedSecondMaterial.likes(), expectedSecondMaterial.likedBy())
        );


        // step 9: user makes a GET request to /educational-content/9999 and system returns NOT_FOUND(404) with message “Content with id 9999 not found”
        // given & when
        ResultActions performGetMaterialNotExistingId = mockMvc.perform(get("/api/educational-content/9999")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        performGetMaterialNotExistingId.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message":  "Content with id 9999 not found",
                        "status": "NOT_FOUND"
                        }
                        """.trim()));


        // step 10: user makes a GET request to /educational-content/1 and system returns OK(200) with content
        // given & when
        ResultActions performGetMaterialById = mockMvc.perform(get("/api/educational-content/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult performGetMaterialByIdMvcResult = performGetMaterialById.andExpect(status().isOk()).andReturn();
        String jsonWithMaterialById = performGetMaterialByIdMvcResult.getResponse().getContentAsString();
        EducationalMaterial materialById = objectMapper.readValue(jsonWithMaterialById, new TypeReference<>() {
        });

        assertThat(materialById).isEqualTo(getExpectedMaterialAboutQuantumPhysics());


        // step 11: user makes a GET request to /educational-content/1/comments and system returns OK(200) with list of comments for educational content with id: 1
        // given & when
        ResultActions performGetComments = mockMvc.perform(get("/api/educational-content/1/comments")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult performGetCommentsMvcResult = performGetComments.andExpect(status().isOk()).andReturn();
        String jsonWithComments = performGetCommentsMvcResult.getResponse().getContentAsString();
        List<Comment> comments = objectMapper.readValue(jsonWithComments, new TypeReference<>() {
        });

        assertThat(comments).hasSize(1);

        // step 12: user makes a POST request to /educational-content/1/comments with his comment and system returns OK(200) with list of comments
        // given
        String commentText = "pozytywny komentarz";

        // when
        ResultActions performPostComments = mockMvc.perform(post("/api/educational-content/1/comments")
                .header("Authorization", "Bearer " + token)
                .content(commentText)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult performPostCommentsMvcResult = performPostComments.andExpect(status().isOk()).andReturn();
        String jsonWithPostedComment = performPostCommentsMvcResult.getResponse().getContentAsString();
        List<Comment> commentsWithPostedComment = objectMapper.readValue(jsonWithPostedComment, new TypeReference<>() {
        });

        assertThat(commentsWithPostedComment).hasSize(2);
        Comment postedComment = commentsWithPostedComment.get(1);
        assertThat(postedComment.text()).isEqualTo(commentText);
        assertThat(postedComment.author()).isEqualTo("someUser");

        // step 13: user makes a Put to /educational-content/1/likes and system increase likes count by 1, and returns OK(200)
        // given & when
        ResultActions performPutLikes = mockMvc.perform(put("/api/educational-content/1/likes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult performPutLikesMvcResult = performPutLikes.andExpect(status().isOk()).andReturn();
        String jsonWithLikesCount = performPutLikesMvcResult.getResponse().getContentAsString();
        Integer likesCount = objectMapper.readValue(jsonWithLikesCount, new TypeReference<>() {
        });

        assertThat(likesCount).isEqualTo(11);

        // step 14: user makes a PUT to /educational-content/1/likes and system don't increase likes count, and returns CONFLICT(409) because user already liked
        // given && when
        ResultActions performPutLikesAgain = mockMvc.perform(put("/api/educational-content/1/likes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        performPutLikesAgain.andExpect(status().isConflict())
                .andExpect(content().json("""
                        {
                        "message":  "User with username: someUser already liked EducationalMaterial",
                        "status": "CONFLICT"
                        }
                        """.trim()));

        // step 15: user makes DELETE to /educational-content/1/likes and system decrease likes count by 1, and return OK(200)
        // given && when
        ResultActions performDeleteLikes = mockMvc.perform(delete("/api/educational-content/1/likes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult performDeleteLikesMvcResult = performDeleteLikes.andExpect(status().isOk()).andReturn();
        String jsonWithLikes = performDeleteLikesMvcResult.getResponse().getContentAsString();
        Integer likes = objectMapper.readValue(jsonWithLikes, new TypeReference<>() {
        });
        assertThat(likes).isEqualTo(10);

        // step 16: user makes DELETE to /educational-content/1/likes and system don't decrease likes count, and return CONFLICT(409) because user didn't liked yet
        // given && when
        ResultActions performDeleteLikesAgain = mockMvc.perform(delete("/api/educational-content/1/likes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        performDeleteLikesAgain.andExpect(status().isConflict())
                .andExpect(content().json("""
                        {
                        "message":  "User with username: someUser didn't liked EducationalMaterial yet",
                        "status": "CONFLICT"
                        }
                        """.trim()));

    }

}