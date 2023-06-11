package com.studymate.infrastructure.reminder.controller;

import com.studymate.domain.learningreminder.LearningReminderFacade;
import com.studymate.domain.learningreminder.Reminder;
import com.studymate.domain.learningreminder.dto.ReminderData;
import com.studymate.domain.user.User;
import com.studymate.domain.user.UserManagementFacade;
import com.studymate.infrastructure.reminder.controller.dto.CreateReminderRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@AllArgsConstructor
public class ReminderRestController {

private final LearningReminderFacade learningReminderFacade;
private final UserManagementFacade userManagementFacade;
    @GetMapping
    public ResponseEntity<List<Reminder>> getRemindersForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userManagementFacade.findUserByUsername(currentUserName);
        List<Reminder> reminders = learningReminderFacade.getRemindersForCurrentUser(user.userId());
        return ResponseEntity.ok(reminders);
    }

    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody CreateReminderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userManagementFacade.findUserByUsername(currentUserName);
        ReminderData reminderData = new ReminderData(user.userId(), request.message(), request.reminderTime());
        Reminder reminder = learningReminderFacade.createReminder(reminderData);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reminder.id())
                .toUri();

        return ResponseEntity.created(location).body(reminder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable String id) {
        learningReminderFacade.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

}
