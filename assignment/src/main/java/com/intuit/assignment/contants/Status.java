package com.intuit.assignment.contants;

public enum Status {

    ACTIVE("Active"),
    INACTIVE("Inactive"),
    APPROVED("Approved");
    private final String message;
    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
