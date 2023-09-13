package com.intuit.assignment.contants;

public enum ErrorType {

    UNHANDLED_EXCEPTION("Internal server errors occured"),
    RESOURCE_NOT_FOUND("Resource not found"),
    INVALID_INPUT("Input is not as expected"),
    BAD_REQUEST("Request is not well formed");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
