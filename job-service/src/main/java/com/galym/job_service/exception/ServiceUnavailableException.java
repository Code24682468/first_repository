package com.galym.job_service.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException (String message) {
        super(message);
    }
}
