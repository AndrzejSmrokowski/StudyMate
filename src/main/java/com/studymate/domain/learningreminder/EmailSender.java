package com.studymate.domain.learningreminder;

public interface EmailSender {
    void sendEmail(String to, String subject, String text);
}