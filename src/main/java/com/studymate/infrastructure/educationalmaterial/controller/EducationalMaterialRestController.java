package com.studymate.infrastructure.educationalmaterial.controller;

import com.studymate.domain.educationalmaterial.Comment;
import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.EducationalMaterialFacade;
import com.studymate.domain.educationalmaterial.dto.CommentData;
import com.studymate.domain.educationalmaterial.dto.EducationalMaterialData;
import com.studymate.domain.progresstracking.ProgressTrackingFacade;
import com.studymate.domain.user.User;
import com.studymate.domain.user.UserManagementFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/educational-content")
@AllArgsConstructor
public class EducationalMaterialRestController {

    private final EducationalMaterialFacade educationalMaterialFacade;
    private final UserManagementFacade userManagementFacade;
    private final ProgressTrackingFacade progressTrackingFacade;

    @GetMapping
    public ResponseEntity<List<EducationalMaterial>> getEducationalMaterials() {
        List<EducationalMaterial> educationalMaterials = educationalMaterialFacade.getEducationalMaterials();
        return ResponseEntity.ok(educationalMaterials);
    }

    @PostMapping
    public ResponseEntity<EducationalMaterial> createEducationalMaterial(@RequestBody EducationalMaterialData materialData) {
        EducationalMaterial educationalMaterial = educationalMaterialFacade.createEducationalMaterial(materialData);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(educationalMaterial.id())
                .toUri();

        return ResponseEntity.created(location).body(educationalMaterial);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationalMaterial> updateEducationalMaterial(@PathVariable String id, @RequestBody EducationalMaterialData materialData) {
        educationalMaterialFacade.updateEducationalMaterial(id, materialData);
        EducationalMaterial educationalMaterial = educationalMaterialFacade.getMaterialById(id);
        return ResponseEntity.ok(educationalMaterial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationalMaterial> getMaterialById(@PathVariable String id) {
        EducationalMaterial educationalMaterialById = educationalMaterialFacade.getMaterialById(id);
        return ResponseEntity.ok(educationalMaterialById);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getMaterialComments(@PathVariable String id) {
        List<Comment> comments = educationalMaterialFacade.getMaterialComments(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> addComment(@PathVariable String id, @RequestBody String text) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        CommentData commentData = new CommentData(text, currentUserName);
        educationalMaterialFacade.addMaterialComment(id, commentData);
        List<Comment> comments = educationalMaterialFacade.getMaterialComments(id);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}/likes")
    public ResponseEntity<Integer> likeMaterial(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        educationalMaterialFacade.likeMaterial(id, currentUserName);
        return ResponseEntity.ok(educationalMaterialFacade.getMaterialById(id).likes());
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<Integer> unlikeMaterial(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        educationalMaterialFacade.unlikeMaterial(id, currentUserName);
        return ResponseEntity.ok(educationalMaterialFacade.getMaterialById(id).likes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable String id) {
        educationalMaterialFacade.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Void> reviewMaterial(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userManagementFacade.findUserByUsername(currentUserName);
        String currentUserId = currentUser.userId();
        EducationalMaterial material = educationalMaterialFacade.getMaterialById(id);
        progressTrackingFacade.addReviewedMaterialIdToProgress(material, currentUserId);
        return ResponseEntity.ok().build();
    }
}


