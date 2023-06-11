package com.studymate.domain.learningreminder;

import com.studymate.domain.user.UserManagementFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LearningReminderConfiguration {

    @Bean
    LearningReminderFacade learningReminderFacade(ReminderRepository repository, EmailSender emailSender, UserManagementFacade userManagementFacade) {
        return new LearningReminderFacade(new ReminderService(repository), emailSender, userManagementFacade);
    }
}
