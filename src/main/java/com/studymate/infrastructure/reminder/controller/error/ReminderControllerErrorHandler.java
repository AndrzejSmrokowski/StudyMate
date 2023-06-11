package com.studymate.infrastructure.reminder.controller.error;

import com.studymate.domain.learningreminder.NoRemindersFoundException;
import com.studymate.domain.learningreminder.ReminderNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ReminderControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoRemindersFoundException.class)
    @ResponseBody
    public ReminderErrorResponse materialNotFound(NoRemindersFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new ReminderErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReminderNotFoundException.class)
    @ResponseBody
    public ReminderErrorResponse materialNotFound(ReminderNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new ReminderErrorResponse(message, HttpStatus.NOT_FOUND);
    }

}
