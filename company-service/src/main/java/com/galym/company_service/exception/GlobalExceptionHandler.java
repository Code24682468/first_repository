package com.galym.company_service.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity <ErrorResponseDto> handleEntityNotFoundException (EntityNotFoundException ex, HttpServletRequest request) {
        log.error("Handle EntityNotFoundException", ex);
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                404,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(exception = {IllegalArgumentException.class})
    public ResponseEntity <ErrorResponseDto> handleIllegalArgumentException (IllegalArgumentException ex, HttpServletRequest request) {
        log.error("Handle IllegalArgumentException", ex);
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                400,
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity <> (error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErrorResponseDto> handleMethodArgumentNotValidException (
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        log.error("Handle MethodArgumentNotValidException", ex);
        Map <String, String> errors = new HashMap <> ();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponseDto error = new ErrorResponseDto (
                LocalDateTime.now(),
                400,
                "VALIDATION_ERROR",
                errors.values().toString(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
