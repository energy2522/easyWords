package com.maiboroda.easyWords.handler;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.maiboroda.easyWords.dto.ApiError;
import com.maiboroda.easyWords.exception.CollectionNotFoundException;
import com.maiboroda.easyWords.exception.UserNotFoundException;
import com.maiboroda.easyWords.exception.WordNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String MESSAGE_SEPARATOR = ", ";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationHandler(ConstraintViolationException ex) {
        log.error("Validation error {}", ex.getLocalizedMessage());

        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(
                Collectors.joining(MESSAGE_SEPARATOR));

        return buildResponse(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        log.error("Validation error {}", ex.getLocalizedMessage());

        String message = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return buildResponse(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
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

    @ExceptionHandler({CollectionNotFoundException.class, WordNotFoundException.class, UserNotFoundException.class })
    public ResponseEntity<Object> systemObjectNotFound(RuntimeException ex) {
        return buildResponse(new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgument(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Wrong argument type for value '%s'. Expected '%s'", ex.getValue(),
                ex.getParameter().getParameter().getType().getSimpleName());

        return buildResponse(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
    }

    private ResponseEntity<Object> buildResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
