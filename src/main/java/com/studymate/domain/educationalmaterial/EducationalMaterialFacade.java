package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.CommentData;
import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class EducationalMaterialFacade {

    private final EducationalMaterialService educationalMaterialService;
    private final MaterialLikeManager materialLikeManager;

    public List<EducationalMaterial> getEducationalMaterials() {
        return educationalMaterialService.getEducationalMaterials();
    }

    public EducationalMaterial createEducationalMaterial(EducationalMaterialData materialData) {
        EducationalMaterial educationalMaterial = EducationalMaterialMapper.mapToEducationalMaterial(materialData);
        return educationalMaterialService.createEducationalMaterial(educationalMaterial);
    }
    public EducationalMaterial createEducationalMaterial(EducationalMaterial material) {
        return educationalMaterialService.createEducationalMaterial(material);
    }

    public void updateEducationalMaterial(String materialId, EducationalMaterialData materialData) {
        educationalMaterialService.updateEducationalMaterial(materialId, materialData);
    }

    public EducationalMaterial getMaterialById(String materialId) {
        return educationalMaterialService.getMaterialById(materialId);
    }

    public void approveMaterial(String materialId) {
        EducationalMaterial material = getMaterialById(materialId);
        EducationalMaterial approvedMaterial = EducationalMaterialMapper.mapToApprovedEducationalMaterial(material);
        educationalMaterialService.updateEducationalMaterial(materialId, approvedMaterial);
    }

    public void rejectMaterial(String materialId) {
        EducationalMaterial material = getMaterialById(materialId);
        EducationalMaterial rejectedMaterial = EducationalMaterialMapper.mapToRejectedEducationalMaterial(material);
        educationalMaterialService.updateEducationalMaterial(materialId, rejectedMaterial);
    }

    public List<Comment> getMaterialComments(String materialId) {
        EducationalMaterial material = getMaterialById(materialId);
        return material.comments();
    }
    public void addMaterialComment(String materialId, CommentData commentData) {
        EducationalMaterial material = getMaterialById(materialId);
        material.addComment(CommentMapper.mapToComment(commentData));
        educationalMaterialService.updateEducationalMaterial(materialId, material);
    }

    public void likeMaterial(String materialId, String username) {
        materialLikeManager.likeMaterial(materialId, username);
    }

    public void unlikeMaterial(String materialId, String username) {
        materialLikeManager.unlikeMaterial(materialId, username);
    }
}
