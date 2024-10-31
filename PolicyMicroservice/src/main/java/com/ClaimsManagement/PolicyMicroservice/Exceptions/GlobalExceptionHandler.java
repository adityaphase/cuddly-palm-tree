package com.ClaimsManagement.PolicyMicroservice.Exceptions;

import com.ClaimsManagement.PolicyMicroservice.Service.ErrorResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PolicyIdNotFoundException.class)
    public ResponseEntity<ErrorResponseService> handlePolicyIdNotFoundException(PolicyIdNotFoundException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseService);
    }

    @ExceptionHandler(MalformedTokenException.class)
    public ResponseEntity<ErrorResponseService> handleMalformedTokenException(MalformedTokenException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseService);
    }
}
