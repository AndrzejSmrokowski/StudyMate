package com.studymate.domain.educationalmaterial;

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


}
