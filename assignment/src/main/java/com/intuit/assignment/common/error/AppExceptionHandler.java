package com.intuit.assignment.common.error;

import com.intuit.assignment.contants.ErrorType;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.intuit.assignment.contants.ErrorType.*;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private AppResponseHttpStatus appResponseHttpStatus = new AppResponseHttpStatus();

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<ErrorResponse> handleAppException(AppException apEx){
        ErrorType errorType = apEx.getErrorType();
        ErrorResponse errorResponse = new ErrorResponse(errorType,apEx.getMessage(),apEx.getErrors());
        return new ResponseEntity<>(errorResponse,appResponseHttpStatus.getStatus(errorType));
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<ErrorObject> errors = new ArrayList<ErrorObject>();
        for (Throwable violation : ex.getSQLException()) {
            errors.add(new ErrorObject(violation.getClass().getName() , violation.getMessage()));
        }

        ErrorResponse errorResponse =
                new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(
                errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleUncaughtException(Exception ex){
        ErrorObject errorObject = new ErrorObject(UNHANDLED_EXCEPTION.getMessage(), ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse(UNHANDLED_EXCEPTION,UNHANDLED_EXCEPTION.getMessage(), List.of(errorObject));

        return new ResponseEntity<>(errorResponse,appResponseHttpStatus.getStatus(UNHANDLED_EXCEPTION));
    }
}
