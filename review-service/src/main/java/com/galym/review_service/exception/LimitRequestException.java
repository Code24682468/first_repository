package com.galym.review_service.exception;

public class LimitRequestException extends RuntimeException {
    public LimitRequestException(String message) {
        super(message);
    }
}
