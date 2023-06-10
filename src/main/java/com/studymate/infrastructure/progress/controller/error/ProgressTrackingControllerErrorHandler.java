package com.studymate.infrastructure.progress.controller.error;

import com.studymate.domain.progresstracking.ProgressNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ProgressTrackingControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProgressNotFoundException.class)
    @ResponseBody
    public ProgressErrorResponse materialNotFound(ProgressNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new ProgressErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}
