package com.studymate.domain.educationalmaterial;

import com.studymate.domain.educationalmaterial.dto.CommentData;
import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class EducationalMaterialFacadeTest {

    private final EducationalMaterialService educationalMaterialService = new EducationalMaterialService(new InMemoryEducationalMaterialRepository());
    private final EducationalMaterialFacade educationalMaterialFacade = new EducationalMaterialFacade(educationalMaterialService, new MaterialLikeManager(educationalMaterialService));

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

    @Test
    void shouldThrowMaterialNotFoundExceptionWhenEducationalMaterialDontExist() {
        // given
        String materialId = "100";
        // when
        Throwable thrown = catchThrowable(() -> educationalMaterialFacade.getMaterialById(materialId));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(MaterialNotFoundException.class)
                .hasMessage(String.format("Content with id %s not found", materialId));
        
    }

    @Test
    void shouldAddCommentsToEducationalMaterialAndGetComments() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł1", "Opis1", "Treść1");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        CommentData commentData1 = new CommentData("Bardzo fajny materiał", "Przemek");
        CommentData commentData2 = new CommentData("Bardzo fajny materiał", "Przemek");
        // when
        educationalMaterialFacade.addMaterialComment(educationalMaterial.id(), commentData1);
        educationalMaterialFacade.addMaterialComment(educationalMaterial.id(), commentData2);
        // then
        assertThat(educationalMaterialFacade.getMaterialComments(educationalMaterial.id())).hasSize(2);

    }

    @Test
    void shouldApproveEducationalMaterial() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        // when
        educationalMaterialFacade.approveMaterial(educationalMaterial.id());
        // then
        assertThat(educationalMaterialFacade.getMaterialById(educationalMaterial.id()).status()).isEqualTo(MaterialStatus.APPROVED);

    }

    @Test
    void shouldRejectEducationalMaterial() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        // when
        educationalMaterialFacade.rejectMaterial(educationalMaterial.id());
        // then
        assertThat(educationalMaterialFacade.getMaterialById(educationalMaterial.id()).status()).isEqualTo(MaterialStatus.REJECTED);

    }

    @Test
    void shouldAddLikeToEducationalMaterialLikesCount() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        // when
        educationalMaterialFacade.likeMaterial(educationalMaterial.id(), "username");
        // then
        assertThat(educationalMaterialFacade.getMaterialById(educationalMaterial.id()).likes()).isEqualTo(1);

    }

    @Test
    void shouldUnlikeToEducationalMaterial() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        // when
        educationalMaterialFacade.likeMaterial(educationalMaterial.id(), "username");
        educationalMaterialFacade.unlikeMaterial(educationalMaterial.id(), "username");
        // then
        assertThat(educationalMaterialFacade.getMaterialById(educationalMaterial.id()).likes()).isEqualTo(0);

    }

    @Test
    void shouldThrowExceptionIfMaterialIsAlreadyLikeByUser() {
        // given
        EducationalMaterialData materialData = new EducationalMaterialData("Tytuł", "Opis", "Treść");
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);
        String username = "username";
        educationalMaterialFacade.likeMaterial(educationalMaterial.id(), username);
        // when
        Throwable thrown = catchThrowable(() -> educationalMaterialFacade.likeMaterial(educationalMaterial.id(), username));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(AlreadyLikedException.class)
                .hasMessage(String.format("User with username: %s already liked EducationalMaterial", username));


    }
}
