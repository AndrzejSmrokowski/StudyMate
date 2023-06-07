package com.studymate.infrastructure.educationalmaterial.controller;

import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.EducationalMaterialFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educational-content")
@AllArgsConstructor
public class EducationalMaterialRestController {

    private final EducationalMaterialFacade educationalMaterialFacade;

    @GetMapping
    public ResponseEntity<List<EducationalMaterial>> getEducationalMaterials() {
        List<EducationalMaterial> educationalMaterials = educationalMaterialFacade.getEducationalMaterials();
        return ResponseEntity.ok(educationalMaterials);
    }



}


