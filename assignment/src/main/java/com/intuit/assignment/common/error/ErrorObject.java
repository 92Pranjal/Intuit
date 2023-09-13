package com.intuit.assignment.common.error;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorObject
        implements Serializable {
    private static final long serialVersionUID = -1257157290745766939L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String type;

    private final String name;

    private final String message;

    public ErrorObject(String name, String message) {
        this(null, name, message);
    }

    public ErrorObject(String type, String name, String message) {
        this.type = type;
        this.name = name;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}

