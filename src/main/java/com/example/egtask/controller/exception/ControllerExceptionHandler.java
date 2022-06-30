package com.example.egtask.controller.exception;

import com.example.egtask.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleError(ResourceNotFoundException ex, WebRequest request) {
        log.error(ex.toString(), ex);
        return new ResponseEntity<>(Map.of("missing resource identifier", ex.getResourceIdentifier()), HttpStatus.NOT_FOUND);
    }
}
