package com.studymate.domain.user;

import com.studymate.domain.user.dto.RegisterData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;


class UserManagementFacadeTest {
    private final UserManagementFacade userManagementFacade = new UserManagementFacade(new InMemoryUserRepository());

    @Test
    void shouldRegisterUser() {
        // given
        RegisterData registerData = new RegisterData("username", "passworderinio123");

        // when
        User user = userManagementFacade.registerUser(registerData);

        // then
        assertThat(user.username()).isEqualTo("username");

    }

    @Test
    void shouldFindUserByUsername() {
        // given
        RegisterData registerData = new RegisterData("username", "passworderinio123");
        User user = userManagementFacade.registerUser(registerData);

        // when
        User foundUser = userManagementFacade.findUserByUsername(user.username());

        //then
        assertThat(foundUser.username()).isEqualTo(user.username());

    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        String username = "notExistingUser69";

        // when
        Throwable thrown = catchThrowable(() -> userManagementFacade.findUserByUsername(username));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with the given username not found: " + username);

    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExistAndCantRegisterUser() {
        // given
        RegisterData registerData = new RegisterData("username", "passworderinio123");
        userManagementFacade.registerUser(registerData);
        RegisterData registerDataWithTheSameUsername = new RegisterData("username", "password");

        // when
        Throwable thrown = catchThrowable(() -> userManagementFacade.registerUser(registerDataWithTheSameUsername));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User with the given username already exists: username");

    }
}