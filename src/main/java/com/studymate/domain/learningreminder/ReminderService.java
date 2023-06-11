package com.studymate.domain.learningreminder;

import com.studymate.domain.learningreminder.dto.ReminderData;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;

    public List<Reminder> getDueReminders() {
        return reminderRepository.findDueReminders(LocalDateTime.now());
    }

    public List<Reminder> getAllRemindersByUserId(String userId) {
        List<Reminder> reminders = reminderRepository.findAllByUserId(userId);
        if (reminders.isEmpty()) {
            throw new NoRemindersFoundException(userId);
        }
        return reminders;
    }


    public Reminder createReminder(ReminderData reminderData) {
        Reminder reminder = Reminder.builder()
                .reminderTime(reminderData.reminderTime())
                .sent(false)
                .message(reminderData.message())
                .userId(reminderData.userId())
                .build();

        return reminderRepository.save(reminder);
    }

    public void deleteReminder(String reminderId) {
        reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ReminderNotFoundException(reminderId));
        reminderRepository.deleteById(reminderId);
    }

    public Reminder updateReminder(Reminder sendedReminder) {
        return reminderRepository.save(sendedReminder);
    }
}
