package com.studymate.infrastructure.progress.controller;

import com.studymate.domain.progresstracking.Progress;
import com.studymate.domain.progresstracking.ProgressTrackingFacade;
import com.studymate.domain.user.User;
import com.studymate.domain.user.UserManagementFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
@AllArgsConstructor
public class ProgressTrackingController {
    private final ProgressTrackingFacade progressTrackingFacade;
    private final UserManagementFacade userManagementFacade;

    @GetMapping
    public ResponseEntity<Progress> getProgress() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userManagementFacade.findUserByUsername(currentUserName);
        String currentUserId = currentUser.userId();
        Progress progress = progressTrackingFacade.getProgressByUserId(currentUserId);
        return ResponseEntity.ok(progress);
    }

}
