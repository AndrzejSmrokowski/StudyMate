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
        return reminderRepository.findAllByUserId(userId);
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
}
