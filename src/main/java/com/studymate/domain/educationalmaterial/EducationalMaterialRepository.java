package com.studymate.domain.educationalmaterial;

import java.util.List;
import java.util.Optional;

public interface EducationalMaterialRepository {
    List<EducationalMaterial> findAll();

    EducationalMaterial save(EducationalMaterial educationalMaterial);

    Optional<EducationalMaterial> getMaterialById(String materialId);
}
