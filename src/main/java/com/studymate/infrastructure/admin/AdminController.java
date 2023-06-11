package com.studymate.infrastructure.admin;

import com.studymate.domain.admin.AdministrationFacade;
import com.studymate.domain.educationalmaterial.EducationalMaterialFacade;
import com.studymate.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AdminController {

    private final AdministrationFacade administrationFacade;
    private final EducationalMaterialFacade educationalMaterialFacade;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(administrationFacade.getUsers());
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        administrationFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/educationalMaterials/{materialId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveEducationalMaterial(@PathVariable String materialId) {
        educationalMaterialFacade.approveMaterial(materialId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/educationalMaterials/{materialId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> rejectEducationalMaterial(@PathVariable String materialId) {
        educationalMaterialFacade.rejectMaterial(materialId);
        return ResponseEntity.noContent().build();
    }
}
