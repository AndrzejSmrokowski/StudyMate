package com.studymate.infrastructure.testingmodule.controller.error;

import com.studymate.domain.testingmodule.ResultsNotFoundException;
import com.studymate.domain.testingmodule.TestNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class TestingModuleControllerErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TestNotFoundException.class)
    @ResponseBody
    public TestingModuleErrorResponse testNotFound(TestNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new TestingModuleErrorResponse(message, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResultsNotFoundException.class)
    @ResponseBody
    public TestingModuleErrorResponse testNotFound(ResultsNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new TestingModuleErrorResponse(message, HttpStatus.NOT_FOUND);
    }

}
