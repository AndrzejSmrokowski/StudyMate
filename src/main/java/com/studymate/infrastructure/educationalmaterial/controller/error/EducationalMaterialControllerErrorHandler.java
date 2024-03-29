package com.studymate.infrastructure.educationalmaterial.controller.error;

import com.studymate.domain.educationalmaterial.AlreadyLikedException;
import com.studymate.domain.educationalmaterial.MaterialNotFoundException;
import com.studymate.domain.educationalmaterial.NotLikedYetException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class EducationalMaterialControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MaterialNotFoundException.class)
    @ResponseBody
    public EducationalMaterialErrorResponse materialNotFound(MaterialNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new EducationalMaterialErrorResponse(message, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyLikedException.class)
    @ResponseBody
    public EducationalMaterialErrorResponse materialAlreadyLiked(AlreadyLikedException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new EducationalMaterialErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotLikedYetException.class)
    @ResponseBody
    public EducationalMaterialErrorResponse materialNotLikedYet(NotLikedYetException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new EducationalMaterialErrorResponse(message, HttpStatus.CONFLICT);
    }

}

