package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


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
    @Test
    void shouldUpdateEducationalMaterial() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");
        EducationalMaterialData updatedMaterialData = new EducationalMaterialData("Tytuł2", "Opis2", "Treść2");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);

        // when
        educationalMaterialFacade.updateEducationalMaterial(educationalMaterial.id(), updatedMaterialData);
        EducationalMaterial retrievedMaterial = educationalMaterialFacade.getMaterialById(educationalMaterial.id());

        // then
        assertAll("Educational Material",
                () -> assertEquals(updatedMaterialData.title(), retrievedMaterial.title()),
                () -> assertEquals(updatedMaterialData.description(), retrievedMaterial.description()),
                () -> assertEquals(updatedMaterialData.content(), retrievedMaterial.content())

        );
    }
    @Test
    void shouldCreate4EducationalMaterialsAndReturnListOfEducationalMaterials() {
        // given
        EducationalMaterialData materialData1 = new EducationalMaterialData("Tytuł1", "Opis1", "Treść1");
        EducationalMaterialData materialData2 = new EducationalMaterialData("Tytuł2", "Opis2", "Treść2");
        EducationalMaterialData materialData3 = new EducationalMaterialData("Tytuł3", "Opis3", "Treść3");
        EducationalMaterialData materialData4 = new EducationalMaterialData("Tytuł4", "Opis4", "Treść4");

        // when
        educationalMaterialFacade.createEducationalMaterial(materialData1);
        educationalMaterialFacade.createEducationalMaterial(materialData2);
        educationalMaterialFacade.createEducationalMaterial(materialData3);
        educationalMaterialFacade.createEducationalMaterial(materialData4);

        // then
        assertThat(educationalMaterialFacade.getEducationalMaterials()).hasSize(4);

    }
}
