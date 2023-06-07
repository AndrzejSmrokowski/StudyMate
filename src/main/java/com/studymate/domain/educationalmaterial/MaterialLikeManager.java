package com.studymate.domain.educationalmaterial;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class MaterialLikeManager {

    private final EducationalMaterialService educationalMaterialService;

    public void likeMaterial(String materialId, String username) {
        EducationalMaterial material = educationalMaterialService.getMaterialById(materialId);
        Set<String> likedBy = material.likedBy();
        if(likedBy.contains(username)) throw new AlreadyLikedException(username);
        likedBy.add(username);
        int likes = material.likes() + 1;
        EducationalMaterial likedMaterial = EducationalMaterial.builder()
                .title(material.title())
                .content(material.content())
                .description(material.description())
                .comments(material.comments())
                .status(material.status())
                .likes(likes)
                .likedBy(likedBy)
                .build();
        educationalMaterialService.updateEducationalMaterial(materialId, likedMaterial);
    }

    public void unlikeMaterial(String materialId, String username) {
        EducationalMaterial material = educationalMaterialService.getMaterialById(materialId);
        Set<String> likedBy = material.likedBy();
        if(!likedBy.contains(username)) throw new NotLikedYetException(username);
        likedBy.remove(username);
        int likes  = material.likes() - 1;
        EducationalMaterial unlikedMaterial = EducationalMaterial.builder()
                .title(material.title())
                .content(material.content())
                .description(material.description())
                .comments(material.comments())
                .status(material.status())
                .likes(likes)
                .likedBy(likedBy)
                .build();
        educationalMaterialService.updateEducationalMaterial(materialId, unlikedMaterial);
    }
}
