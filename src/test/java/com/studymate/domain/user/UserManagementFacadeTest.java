package com.studymate.domain.user;

import com.studymate.domain.progresstracking.ProgressRepository;
import com.studymate.domain.progresstracking.ProgressTrackingFacade;
import com.studymate.domain.user.dto.RegisterUserDto;
import com.studymate.domain.user.dto.RegistrationResultDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


class UserManagementFacadeTest {
    private final ProgressTrackingFacade progressTrackingFacade = mock(ProgressTrackingFacade.class);
    private final ProgressRepository progressRepository = mock(ProgressRepository.class);
    private final UserManagementFacade userManagementFacade = new UserManagementFacade(new InMemoryUserRepository(),new BCryptPasswordEncoder(), progressTrackingFacade, progressRepository);

    @Test
    void shouldRegisterUser() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "passworderinio123");

        // when
        RegistrationResultDto registrationResult = userManagementFacade.registerUser(registerUserDto);


        // then
        assertThat(registrationResult.username()).isEqualTo("username");

    }

    @Test
    void shouldFindUserByUsername() {
        // given
        String username = "username";
        RegisterUserDto registerUserDto = new RegisterUserDto(username, "passworderinio123");
        RegistrationResultDto registrationResult = userManagementFacade.registerUser(registerUserDto);

        // when
        User foundUser = userManagementFacade.findUserByUsername(registrationResult.username());

        //then
        assertThat(foundUser.username()).isEqualTo(username);

    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        String username = "notExistingUser69";

        // when
        Throwable thrown = catchThrowable(() -> userManagementFacade.findUserByUsername(username));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("User with the given username not found: " + username);

    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExistAndCantRegisterUser() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "passworderinio123");
        userManagementFacade.registerUser(registerUserDto);
        RegisterUserDto registerUserDtoWithTheSameUsername = new RegisterUserDto("username", "password");

        // when
        Throwable thrown = catchThrowable(() -> userManagementFacade.registerUser(registerUserDtoWithTheSameUsername));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User with the given username already exists: username");

    }
}