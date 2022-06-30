package com.example.egtask.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {
    @Getter
    private Object resourceIdentifier;

    public ResourceNotFoundException(String message, Object resourceIdentifier) {
        super(message);
        this.resourceIdentifier = resourceIdentifier;
    }
}
