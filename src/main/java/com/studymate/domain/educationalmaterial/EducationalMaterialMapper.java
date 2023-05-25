package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;

public class EducationalMaterialMapper {
    public static EducationalMaterial mapToEducationalMaterial(EducationalMaterialData materialData) {
        return EducationalMaterial.builder()
                .title(materialData.title())
                .content(materialData.content())
                .description(materialData.description())
                .build();
    }
}
