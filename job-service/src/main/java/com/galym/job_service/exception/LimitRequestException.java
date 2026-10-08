package com.galym.job_service.exception;

public class LimitRequestException extends RuntimeException {
    public LimitRequestException (String message) {
        super(message);
    }
}
