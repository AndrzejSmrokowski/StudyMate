package com.studymate.domain.educationalmaterial;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEducationalMaterialRepository implements EducationalMaterialRepository{

    Map<String, EducationalMaterial> database = new ConcurrentHashMap<>();

    @Override
    public List<EducationalMaterial> findAll() {
        return null;
    }

    @Override
    public EducationalMaterial save(EducationalMaterial educationalMaterial) {
        UUID id = UUID.randomUUID();
         EducationalMaterial savedEducationalMaterial = new EducationalMaterial(
                 id.toString(),
                 educationalMaterial.title(),
                 educationalMaterial.description(),
                 educationalMaterial.content()
         );
         database.put(id.toString(), savedEducationalMaterial);
        return savedEducationalMaterial;
    }

    @Override
    public Optional<EducationalMaterial> getMaterialById(String materialId) {
        return Optional.ofNullable(database.get(materialId));
    }
}
