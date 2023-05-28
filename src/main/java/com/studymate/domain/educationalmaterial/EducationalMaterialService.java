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

        EducationalMaterial updatedMaterial = new EducationalMaterial(
                existingMaterial.id(),
                materialData.title(),
                materialData.description(),
                materialData.content()
        );

        educationalMaterialRepository.save(updatedMaterial);
    }
}
