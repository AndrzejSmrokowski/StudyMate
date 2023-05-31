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
        return educationalMaterialRepository.getMaterialById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException("Educational material not found"));
    }


    public void updateEducationalMaterial(String materialId, EducationalMaterialData materialData) {
        EducationalMaterial existingMaterial = educationalMaterialRepository.getMaterialById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException("Educational material not found"));

        EducationalMaterial updatedMaterial = EducationalMaterial.builder()
                .id(existingMaterial.id())
                .title(materialData.title())
                .description(materialData.description())
                .content(materialData.content())
                .comments(existingMaterial.comments())
                .status(existingMaterial.status())
                .likes(existingMaterial.likes())
                .build();

        educationalMaterialRepository.save(updatedMaterial);
    }
    public void updateEducationalMaterial(String materialId, EducationalMaterial material) {
        EducationalMaterial existingMaterial = educationalMaterialRepository.getMaterialById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException("Educational material not found"));
        EducationalMaterial updatedMaterial = EducationalMaterial.builder()
                .id(existingMaterial.id())
                .title(material.title())
                .description(material.description())
                .content(material.content())
                .comments(material.comments())
                .status(material.status())
                .likes(material.likes())
                .build();
        educationalMaterialRepository.save(updatedMaterial);
    }
}
