package com.intuit.assignment.common.error;

import com.intuit.assignment.contants.ErrorType;
import org.springframework.http.HttpStatus;

public class AppResponseHttpStatus {
    public HttpStatus getStatus(ErrorType e){
        switch (e) {
            case RESOURCE_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case INVALID_INPUT:
                return HttpStatus.BAD_REQUEST;
            case BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }
}
