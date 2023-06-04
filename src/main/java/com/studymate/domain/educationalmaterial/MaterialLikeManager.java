package com.studymate.domain.educationalmaterial;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MaterialLikeManager {

    private final EducationalMaterialService educationalMaterialService;

    public void likeMaterial(String materialId) {
        EducationalMaterial material = educationalMaterialService.getMaterialById(materialId);
        int likes  = material.likes() + 1;
        EducationalMaterial likedMaterial = EducationalMaterial.builder()
                .title(material.title())
                .content(material.content())
                .description(material.description())
                .comments(material.comments())
                .status(material.status())
                .likes(likes)
                .build();
        educationalMaterialService.updateEducationalMaterial(materialId, likedMaterial);
    }

    public void unlikeMaterial(String materialId) {
        EducationalMaterial material = educationalMaterialService.getMaterialById(materialId);
        int likes  = material.likes() - 1;
        EducationalMaterial unlikedMaterial = EducationalMaterial.builder()
                .title(material.title())
                .content(material.content())
                .description(material.description())
                .comments(material.comments())
                .status(material.status())
                .likes(likes)
                .build();
        educationalMaterialService.updateEducationalMaterial(materialId, unlikedMaterial);
    }
}
