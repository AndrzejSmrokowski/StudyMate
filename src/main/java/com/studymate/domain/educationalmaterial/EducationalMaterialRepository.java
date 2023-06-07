package com.studymate.domain.educationalmaterial;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalMaterialRepository extends MongoRepository<EducationalMaterial, String> {

}
