package com.intuit.assignment.common.error;

import com.intuit.assignment.contants.ErrorType;

import java.util.List;

public class AppException extends RuntimeException{

    private static final long serialVersionUID = -1257115719074576939L;

    private final ErrorType errorType;

    private final List<ErrorObject> errors;

    public AppException(ErrorType errorType, String message, ErrorObject errors){
        this(errorType,message,List.of(errors));
    }

    public AppException(ErrorType errorType, String message){
        this(errorType,message,List.of());
    }

    public AppException(ErrorType errorType, String message, List<ErrorObject> errors) {
        super(message);
        this.errorType = errorType;
        this.errors = List.copyOf(errors);
    }

    public ErrorType getErrorType(){
        return errorType;
    }

    public List<ErrorObject> getErrors() {
        return errors;
    }


}
