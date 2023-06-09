package com.studymate.domain.testingmodule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestingModuleConfiguration {
    @Bean
    TestingModuleFacade testingModuleFacade(TestRepository testRepository, TestResultRepository resultRepository) {
        return new TestingModuleFacade(new TestService(testRepository, resultRepository), new TestSolver());
    }
}
