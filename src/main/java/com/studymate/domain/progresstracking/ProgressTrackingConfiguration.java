package com.studymate.domain.progresstracking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProgressTrackingConfiguration {
    @Bean
    ProgressTrackingFacade progressTrackingFacade(ProgressRepository repository) {
        return new ProgressTrackingFacade(new ProgressTrackingService(repository));
    }
}
