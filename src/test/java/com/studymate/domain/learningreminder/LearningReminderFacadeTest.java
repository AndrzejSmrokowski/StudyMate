package com.studymate.domain.learningreminder;

import com.studymate.domain.learningreminder.dto.ReminderData;
import com.studymate.domain.user.UserManagementFacade;
import com.studymate.domain.user.dto.UserData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class LearningReminderFacadeTest {
private final EmailSender emailSender = mock(EmailSender.class);
private final UserManagementFacade userManagementFacade = mock(UserManagementFacade.class);
    private final LearningReminderFacade learningReminderFacade = new LearningReminderFacade(new ReminderService(new InMemoryReminderRepository()), emailSender, userManagementFacade);

    @Test
    void shouldGetDueRemindersForCurrentUser() {
        // given
        String userId = "user123";
        ReminderData reminderData = new ReminderData(userId,"Test Message", LocalDateTime.now());
        learningReminderFacade.createReminder(reminderData);

        // when
        var reminders = learningReminderFacade.getDueRemindersForCurrentUser(userId);

        // then
        assertThat(reminders).isNotEmpty();
    }

    @Test
    void shouldSendDueReminders() {
        // given
        String userId = "user123";
        String userEmail = "user123@example.com";
        UserData userData = new UserData(userId, null, null, userEmail);
        when(userManagementFacade.getUserData(userId)).thenReturn(userData);
        ReminderData reminderData = new ReminderData("user123","Test Message", LocalDateTime.now());
        learningReminderFacade.createReminder(reminderData);

        // when
        learningReminderFacade.sendDueReminders();

        // then
        verify(emailSender, atLeastOnce()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void shouldCreateReminder() {
        // given
        ReminderData reminderData = new ReminderData("user123","Test Message", LocalDateTime.now());

        // when
        Reminder reminder = learningReminderFacade.createReminder(reminderData);

        // then
        assertAll(
                () -> assertThat(reminder.message()).isEqualTo(reminderData.message()),
                () -> assertThat(reminder.userId()).isEqualTo(reminderData.userId()),
                () -> assertThat(reminder.reminderTime()).isEqualTo(reminderData.reminderTime())
        );

    }

    @Test
    void shouldThrowNoRemindersFoundExceptionForCurrentUser() {
        // given
        String userId = "user123";

        //when
        Throwable thrown = catchThrowable(() -> learningReminderFacade.getDueRemindersForCurrentUser(userId));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(NoRemindersFoundException.class)
                .hasMessage(String.format("No reminders found for user with id: %s", userId));
    }



}
