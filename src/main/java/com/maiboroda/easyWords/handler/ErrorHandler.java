package com.maiboroda.easyWords.handler;

import javax.validation.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maiboroda.easyWords.dto.ApiError;
import com.maiboroda.easyWords.exception.CollectionNotFoundException;
import com.maiboroda.easyWords.exception.WordNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> validationHandler(ValidationException ex) {
        log.error("Validation error {}", ex.getLocalizedMessage());
        return buildResponse(new ApiError(HttpStatus.BAD_REQUEST, ex));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> emptyResultHandler(EmptyResultDataAccessException ex) {
        log.error("Object not found {}", ex.getLocalizedMessage());
        return buildResponse(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> violationHandler(DataIntegrityViolationException ex) {
        return buildResponse(new ApiError(HttpStatus.BAD_REQUEST, ex));
    }

    @ExceptionHandler({ CollectionNotFoundException.class, WordNotFoundException.class })
    public ResponseEntity<Object> systemObjectNotFound(RuntimeException ex) {
        return buildResponse(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    private ResponseEntity<Object> buildResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
