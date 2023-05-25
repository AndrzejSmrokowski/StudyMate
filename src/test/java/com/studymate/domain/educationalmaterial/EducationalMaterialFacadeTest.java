package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EducationalMaterialFacadeTest {
    private final EducationalMaterialFacade educationalMaterialFacade = new EducationalMaterialFacade(new EducationalMaterialService(new InMemoryEducationalMaterialRepository()));

    @Test
    void shouldCreateAndGetEducationalMaterial() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");

        // when
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        EducationalMaterial retrievedMaterial = educationalMaterialFacade.getMaterialById(educationalMaterial.id());

        // then
        assertAll("Educational Material",
                () -> assertEquals(materialData.title(), retrievedMaterial.title()),
                () -> assertEquals(materialData.description(), retrievedMaterial.description()),
                () -> assertEquals(materialData.content(), retrievedMaterial.content())
        );
    }
}
