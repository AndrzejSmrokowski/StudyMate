package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EducationalMaterialService {

    private final EducationalMaterialRepository educationalMaterialRepository;


    public List<EducationalMaterial> getEducationalMaterials() {
        return educationalMaterialRepository.findAll();
    }

    public EducationalMaterial createEducationalMaterial(EducationalMaterial educationalMaterial) {
        return educationalMaterialRepository.save(educationalMaterial);
    }

    public EducationalMaterial getMaterialById(String materialId) {
        return educationalMaterialRepository.findById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException(materialId));
    }


    public void updateEducationalMaterial(String materialId, EducationalMaterialData materialData) {
        EducationalMaterial existingMaterial = educationalMaterialRepository.findById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException(materialId));

        EducationalMaterial updatedMaterial = EducationalMaterial.builder()
                .id(existingMaterial.id())
                .title(materialData.title())
                .description(materialData.description())
                .content(materialData.content())
                .comments(existingMaterial.comments())
                .status(existingMaterial.status())
                .likes(existingMaterial.likes())
                .likedBy(existingMaterial.likedBy())
                .build();

        educationalMaterialRepository.save(updatedMaterial);
    }
    public void updateEducationalMaterial(String materialId, EducationalMaterial material) {
        EducationalMaterial existingMaterial = educationalMaterialRepository.findById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException(materialId));
        EducationalMaterial updatedMaterial = EducationalMaterial.builder()
                .id(existingMaterial.id())
                .title(material.title())
                .description(material.description())
                .content(material.content())
                .comments(material.comments())
                .status(material.status())
                .likes(material.likes())
                .likedBy(material.likedBy())
                .build();
        educationalMaterialRepository.save(updatedMaterial);
    }
}
