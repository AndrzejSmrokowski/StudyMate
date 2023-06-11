package com.studymate.scheduler;

import com.studymate.BaseIntegrationTest;
import com.studymate.StudyMateApplication;
import com.studymate.infrastructure.reminder.scheduler.LearningReminderScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = StudyMateApplication.class, properties = "scheduling.enabled=true")
public class LearningReminderSchedulerTest extends BaseIntegrationTest {


    @SpyBean
    LearningReminderScheduler learningReminderScheduler;


    @Test
    void shouldSendDueRemindersExactlyGivenTimes() {
        await().
                atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(learningReminderScheduler, times(1)).sendDueReminders());
    }


}
