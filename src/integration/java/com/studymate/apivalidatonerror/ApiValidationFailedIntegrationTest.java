package com.studymate.apivalidatonerror;

import com.studymate.BaseIntegrationTest;
import com.studymate.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void shouldReturn400BadRequestAndValidationMessageWhenEmptyAndNullInReminderRequest() throws Exception {
        // given
        String invalidReminderRequestJson = """
                {
                "message": "",
                "reminderTime": null
                }
                """;

        // when
        ResultActions performPostReminder = mockMvc.perform(MockMvcRequestBuilders.post("/api/reminders")
                .content(invalidReminderRequestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult1 = performPostReminder.andExpect(status().isBadRequest()).andReturn();
        String json1 = mvcResult1.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json1, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "message must not be empty",
                "reminderTime must not be null"
        );

    }

    @Test
    @WithMockUser
    public void shouldReturn400BadRequestAndValidationMessageWhenInvalidDataInRegisterUserRequest() throws Exception {
        // given
        String invalidRegisterUserJson =
                """
                {
                "username": "ab",
                "password": "123"
                }
                """.trim();

        // when
        ResultActions performPostRegisterUser = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                .content(invalidRegisterUserJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult = performPostRegisterUser.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertAll(
                () -> assertThat(result.messages()).contains("Username must be between 4 and 15 characters"),
                () -> assertThat(result.messages()).contains("Password must be at least 8")
        );
    }

    @Test
    @WithMockUser
    public void shouldReturn400BadRequestAndValidationMessageWhenInvalidDataInCreateTestRequest() throws Exception {
        // given
        String invalidCreateTestJson =
                """
                {
                "testName": "",
                "questions": null
                }
                """.trim();

        // when
        ResultActions performPostCreateTest = mockMvc.perform(MockMvcRequestBuilders.post("/api/tests")
                .content(invalidCreateTestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult = performPostCreateTest.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertAll(
                () -> assertThat(result.messages()).contains("testName must not be empty"),
                () -> assertThat(result.messages()).contains("questions must not be null")
        );
    }
    @Test
    @WithMockUser
    public void shouldReturn400BadRequestAndValidationMessageWhenInvalidDataInSolveTestRequest() throws Exception {
        // given
        String invalidSolveTestJson =
                """
                {
                "testId": "",
                "answers": null
                }
                """.trim();

        // when
        ResultActions performPostSolveTest = mockMvc.perform(MockMvcRequestBuilders.post("/api/tests/1/submit")
                .content(invalidSolveTestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult = performPostSolveTest.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertAll(
                () -> assertThat(result.messages()).contains("testId must not be empty"),
                () -> assertThat(result.messages()).contains("answers must not be null")
        );
    }

}
