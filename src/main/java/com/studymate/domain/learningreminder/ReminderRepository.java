package com.studymate.domain.learningreminder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {
    List<Reminder> findAllByUserId(String userId);

    @Query("{'reminderTime' : { $lte: ?0 }}")
    List<Reminder> findDueReminders(LocalDateTime now);
}
