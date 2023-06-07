package com.studymate;

import com.studymate.infrastructure.security.jwt.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {JwtConfigurationProperties.class})

public class StudyMateApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyMateApplication.class, args);
    }
}
