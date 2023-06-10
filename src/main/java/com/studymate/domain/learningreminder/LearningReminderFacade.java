package com.studymate.domain.learningreminder;

import com.studymate.domain.learningreminder.dto.ReminderData;
import com.studymate.domain.user.UserManagementFacade;
import com.studymate.domain.user.dto.UserData;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LearningReminderFacade {

    private final ReminderService reminderService;
    private final EmailSender emailSender;
    private final UserManagementFacade userManagementFacade;

    public List<Reminder> getDueRemindersForCurrentUser(String userId) {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> allUserReminders = reminderService.getAllRemindersByUserId(userId);

        List<Reminder> dueReminders = allUserReminders.stream()
                .filter(reminder -> !reminder.reminderTime().isAfter(now))
                .collect(Collectors.toList());

        if (dueReminders.isEmpty()) {
            throw new NoRemindersFoundException(userId);
        }

        return dueReminders;
    }

    public Reminder createReminder(ReminderData reminderData) {
        return reminderService.createReminder(reminderData);
    }

    public void sendDueReminders() {
        List<Reminder> dueReminders = reminderService.getDueReminders();

        for (Reminder reminder : dueReminders) {
            UserData userData = userManagementFacade.getUserData(reminder.userId());
            emailSender.sendEmail(userData.userEmail(), "Przypomnienie o nauce", reminder.message());
        }
    }


}
