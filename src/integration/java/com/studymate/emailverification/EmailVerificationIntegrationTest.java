package com.studymate.emailverification;

import com.studymate.BaseIntegrationTest;
import com.studymate.domain.user.User;
import com.studymate.domain.user.UserRepository;
import com.studymate.domain.user.dto.RegistrationResultDto;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("emailVerificationTests")
public class EmailVerificationIntegrationTest extends BaseIntegrationTest {
@Autowired
    private UserRepository userRepository;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testUserEmailVerificationProcess() throws Exception {
        // step 1: user registers to the system
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
        // step 2: user generates token:
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
        // step 3: user adds email
        // given & when
        ResultActions addEmailAction = mockMvc.perform(put("/api/users/add-email")
                .header("Authorization", "Bearer " + token)
                .content("\"someUser@example.com\"")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //then
        addEmailAction.andExpect(status().isOk());

        // fetch user from DB
        Optional<User> optionalUser = userRepository.findByUsername("someUser");
        assertThat(optionalUser.isPresent()).isTrue();
        User user = optionalUser.get();
        // get token from user object
        String emailToken = user.emailVerificationToken().getToken();

        // step 4: user receives a verification email and clicks the verification link
        // given & when
        ResultActions verificationRequest = mockMvc.perform(post("/api/users/verify-email?token=" + emailToken)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        verificationRequest.andExpect(status().isOk());


    }
}
