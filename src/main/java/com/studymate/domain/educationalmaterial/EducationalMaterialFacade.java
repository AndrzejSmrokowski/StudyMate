package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class EducationalMaterialFacade {

    private final EducationalMaterialService educationalMaterialService;

    public List<EducationalMaterial> getEducationalMaterials() {
        return educationalMaterialService.getEducationalMaterials();
    }

    public EducationalMaterial createEducationalMaterial(EducationalMaterialData materialData) {
        EducationalMaterial educationalMaterial = EducationalMaterialMapper.mapToEducationalMaterial(materialData);
        return educationalMaterialService.createEducationalMaterial(educationalMaterial);
    }

    public void updateEducationalMaterial(String materialId, EducationalMaterialData materialData) {
        educationalMaterialService.updateEducationalMaterial(materialId, materialData);
    }

    public EducationalMaterial getMaterialById(String materialId) {
        return educationalMaterialService.getMaterialById(materialId);
    }
}
