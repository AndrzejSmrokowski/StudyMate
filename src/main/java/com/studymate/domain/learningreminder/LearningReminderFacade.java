package com.studymate.domain.learningreminder;

import com.studymate.domain.learningreminder.dto.ReminderData;
import com.studymate.domain.user.UserManagementFacade;
import com.studymate.domain.user.dto.UserData;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LearningReminderFacade {

    private final ReminderService reminderService;
    private final EmailSender emailSender;
    private final UserManagementFacade userManagementFacade;

    public List<Reminder> getRemindersForCurrentUser(String userId) {
        List<Reminder> allUserReminders = reminderService.getAllRemindersByUserId(userId);

        return allUserReminders.stream()
                .filter(reminder -> !reminder.sent())
                .collect(Collectors.toList());
    }


    public Reminder createReminder(ReminderData reminderData) {
        return reminderService.createReminder(reminderData);
    }

    public void sendDueReminders() {
        List<Reminder> dueReminders = reminderService.getDueReminders();

        for (Reminder reminder : dueReminders) {
            if (reminder.sent()) continue;
            UserData userData = userManagementFacade.getUserData(reminder.userId());
            emailSender.sendEmail(userData.userEmail(), "StudyMate: Przypomnienie o nauce", reminder.message());
            Reminder sendedReminder = reminder.toBuilder().sent(true).build();

            reminderService.updateReminder(sendedReminder);
        }
    }

    public void deleteReminder(String reminderId) {
        reminderService.deleteReminder(reminderId);
    }


}
