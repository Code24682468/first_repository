package com.galym.review_service.exception;

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
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity <ErrorResponseDto> handleEntityNotFoundException (
        EntityNotFoundException ex,
        HttpServletRequest request
    ) {
        log.error("Handle EntityNotFoundException");
        ErrorResponseDto error = new ErrorResponseDto (
                LocalDateTime.now(),
                404,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity <ErrorResponseDto> handleIllegalArgumentException (
        IllegalArgumentException ex,
        HttpServletRequest request
    ) {
        log.error("Handle IllegalArgumentException");
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
        log.error("Handle MethodArgumentNotValidException");
        List <String> errors = new ArrayList <> ();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                400,
                "VALIDATION_ERROR",
                errors.toString(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity <ErrorResponseDto> handleServiceUnavailableException (
            ServiceUnavailableException ex,
            HttpServletRequest request
    ) {
        log.error("Handle ServiceUnavailableException");
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                503,
                "SERVICE_UNAVAILABLE",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(LimitRequestException.class)
    public ResponseEntity <ErrorResponseDto> handleLimitRequestException (
            LimitRequestException ex,
            HttpServletRequest request
    ) {
        log.error("Handle LimitRequestException");
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                429,
                "TOO_MANY_REQUESTS",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(error);
    }

}
