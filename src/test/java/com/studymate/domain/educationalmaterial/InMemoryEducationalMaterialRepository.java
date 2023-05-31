package com.studymate.domain.educationalmaterial;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEducationalMaterialRepository implements EducationalMaterialRepository{

    Map<String, EducationalMaterial> database = new ConcurrentHashMap<>();

    @Override
    public List<EducationalMaterial> findAll() {
        return database.values().stream().toList();
    }

    @Override
    public EducationalMaterial save(EducationalMaterial educationalMaterial) {
        String id;
        if (database.values().stream().anyMatch(material -> material.id().equals(educationalMaterial.id()))) {
            id = educationalMaterial.id();
        } else {
            id = UUID.randomUUID().toString();
        }

         EducationalMaterial savedEducationalMaterial = EducationalMaterial.builder()
                 .id(id)
                 .title(educationalMaterial.title())
                 .description(educationalMaterial.description())
                 .content(educationalMaterial.content())
                 .comments(educationalMaterial.comments())
                 .status(educationalMaterial.status())
                 .likes(educationalMaterial.likes())
                 .build();

         database.put(id, savedEducationalMaterial);
        return savedEducationalMaterial;
    }

    @Override
    public Optional<EducationalMaterial> getMaterialById(String materialId) {
        return Optional.ofNullable(database.get(materialId));
    }
}
