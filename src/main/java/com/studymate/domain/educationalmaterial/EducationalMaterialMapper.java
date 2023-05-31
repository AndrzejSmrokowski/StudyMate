package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;

import java.util.ArrayList;

public class EducationalMaterialMapper {
    public static EducationalMaterial mapToEducationalMaterial(EducationalMaterialData materialData) {
        return EducationalMaterial.builder()
                .title(materialData.title())
                .content(materialData.content())
                .description(materialData.description())
                .comments(new ArrayList<>())
                .status(MaterialStatus.PENDING)
                .likes(0)
                .build();
    }

    public static EducationalMaterial mapToApprovedEducationalMaterial(EducationalMaterial material) {
        return EducationalMaterial.builder()
                .title(material.title())
                .content(material.content())
                .description(material.description())
                .comments(material.comments())
                .status(MaterialStatus.APPROVED)
                .likes(material.likes())
                .build();
    }

    public static EducationalMaterial mapToRejectedEducationalMaterial(EducationalMaterial material) {
        return EducationalMaterial.builder()
                .title(material.title())
                .content(material.content())
                .description(material.description())
                .comments(material.comments())
                .status(MaterialStatus.REJECTED)
                .likes(material.likes())
                .build();
    }
}
