package com.studymate.domain.educationalmaterial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EducationalMaterialConfiguration {

    @Bean
    EducationalMaterialFacade educationalMaterialFacade(EducationalMaterialRepository repository) {
        EducationalMaterialService educationalMaterialService = new EducationalMaterialService(repository);
        return new EducationalMaterialFacade(educationalMaterialService, new MaterialLikeManager(educationalMaterialService));
    }
}
