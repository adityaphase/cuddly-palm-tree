package com.ClaimsManagement.ClaimsMicroservice.Exceptions;

import com.ClaimsManagement.ClaimsMicroservice.Service.ErrorResponseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(InsufficientClaimDetailsException.class)
    public ResponseEntity<ErrorResponseService> handleInsufficientClaimDetailsException(InsufficientClaimDetailsException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseService);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseService> handleDataNotFoundException(DataNotFoundException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService(ex.getMessage(), HttpStatus.FAILED_DEPENDENCY.value());
        return ResponseEntity
                .status(HttpStatus.FAILED_DEPENDENCY)
                .body(errorResponseService);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponseService> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService("Invalid Benefit ID or Hospital ID, this ID may not exist",
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseService);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ErrorResponseService> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService("One or more ID not found and may not exist",
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseService);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException ex){
        try{
            String responseBody = ex.contentUTF8();
            JsonNode errorBody = objectMapper.readTree(responseBody);
            Map<String, String> error = new HashMap<>() {{
                    put("message", errorBody.path("message").asText());
                    put("statusCode", errorBody.path("statusCode").asText());
            }};
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);

        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
