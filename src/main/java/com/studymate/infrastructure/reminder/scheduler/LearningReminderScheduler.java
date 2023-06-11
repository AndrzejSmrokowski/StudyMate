package com.studymate.infrastructure.reminder.scheduler;

import com.studymate.domain.learningreminder.LearningReminderFacade;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class LearningReminderScheduler {
    private final LearningReminderFacade learningReminderFacade;

    @Scheduled(fixedDelayString = "${reminder.scheduler.send.delay}") // 1800000 milliseconds = 30 minutes
    public void sendDueReminders() {
        learningReminderFacade.sendDueReminders();
    }

}
