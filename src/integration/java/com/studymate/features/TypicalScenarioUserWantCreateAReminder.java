package com.studymate.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.studymate.BaseIntegrationTest;
import com.studymate.domain.learningreminder.Reminder;
import com.studymate.infrastructure.reminder.controller.dto.CreateReminderRequest;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantCreateAReminder extends BaseIntegrationTest {
    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Test
    @WithMockUser
    public void userCreatesReminderAndWantToSeeRemindersList() throws Exception {
        // Given
        String reminderId = "reminder123";
        String reminderText = "Test reminder message";
        LocalDateTime reminderTime = LocalDateTime.now().plusHours(1);
        CreateReminderRequest reminderRequest = new CreateReminderRequest(reminderText, reminderTime);

        String reminderJson = objectMapper.writeValueAsString(reminderRequest);
        String loginJson = """
                {
                "username": "someUser",
                "password": "somePassword"
                }
                """;
        mockMvc.perform(post("/api/users/register")
                .content(loginJson.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        ResultActions successLoginRequest = mockMvc.perform(post("/api/users/login")
                .content(loginJson.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        MvcResult mvcResult = successLoginRequest.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(json, JwtResponseDto.class);
        String token = jwtResponse.token();

        // Step 1: User sends a POST request to /api/reminders with data to create reminder and system return CREATED(201)
        //given & when
        ResultActions performPostReminder = mockMvc.perform(post("/api/reminders")
                .header("Authorization", "Bearer " + token)
                .content(reminderJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then 
        MvcResult postReminderMvcResult = performPostReminder.andExpect(status().isCreated()).andReturn();
        String createdReminderResultJson = postReminderMvcResult.getResponse().getContentAsString();
        Reminder createdReminder = objectMapper.readValue(createdReminderResultJson, Reminder.class);
        
        assertAll(
                () -> assertThat(createdReminder.reminderTime()).isEqualTo(reminderTime),
                () -> assertThat(createdReminder.message()).isEqualTo(reminderText)
        );

        // Step 2: User sends a GET request to /api/reminders and system returns OK(200) with list of reminders
        //given & when
        ResultActions performGetReminders = mockMvc.perform(get("/api/reminders")
                .header("Authorization", "Bearer " + token)
                .content(reminderJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        MvcResult getRemindersMvcResult = performGetReminders.andExpect(status().isOk()).andReturn();
        String listOfRemindersJson = getRemindersMvcResult.getResponse().getContentAsString();
        List<Reminder> reminderList = objectMapper.readValue(listOfRemindersJson, new TypeReference<>() {
        });

        assertAll(
                () -> assertThat(reminderList).hasSize(1),
                () -> assertThat(reminderList.get(0).message()).isEqualTo(createdReminder.message()),
                () -> assertThat(reminderList.get(0).userId()).isEqualTo(createdReminder.userId()),
                () -> assertThat(reminderList.get(0).sent()).isEqualTo(false)
        );

        // Step 3: User sends a DELETE request to /api/reminders/{id} where {id} is reminder id and system return NO_CONTENT(204)
        // given
        String locationHeader = postReminderMvcResult.getResponse().getHeader("Location");

        // when
        ResultActions performDeleteReminder = mockMvc.perform(delete(locationHeader)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        performDeleteReminder.andExpect(status().isNoContent());

        // Step 4: User sends a GET request to /api/reminders and system returns NOT_FOUND(404)
        //given & when
        ResultActions performGetReminders1 = mockMvc.perform(get("/api/reminders")
                .header("Authorization", "Bearer " + token)
                .content(reminderJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        performGetReminders1.andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No reminders found for user with id:")));

        // Step 5: User sends a DELETE request to /api/reminders/{id} where {id} is not existing id and system return NOT_FOUND(404)
        performDeleteReminder = mockMvc.perform(delete("/api/reminders/" + reminderId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        performDeleteReminder.andExpect(status().isNotFound());
    }
}
