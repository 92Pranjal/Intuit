package com.intuit.assignment.common.error;

import com.intuit.assignment.contants.ErrorType;
import jakarta.servlet.ServletException;
import lombok.Setter;

import java.util.List;

@Setter
public class ErrorResponse {
    private ErrorType errorType;

    private String message;

    private List<ErrorObject> errors;


    public ErrorResponse(ErrorType errorType, String message, List<ErrorObject> errors) {
        this.errorType = errorType;
        this.message = message;
        this.errors = List.copyOf(errors);
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorObject> getErrors() {
        return errors;
    }
}

