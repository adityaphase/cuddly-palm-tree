package com.ClaimsManagement.MemberMicroservice.Exceptions;

import com.ClaimsManagement.MemberMicroservice.Service.ErrorResponseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(AccessNotPermittedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponseService> handleAccessNotPermittedException(AccessNotPermittedException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponseService);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseService> handleMissingRequestHeaderException(MissingRequestHeaderException ex){
        ErrorResponseService errorResponseService = new ErrorResponseService(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseService);
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleFeignException(FeignException ex){
        try{
            String responseBody = ex.contentUTF8();
            JsonNode errorBody = objectMapper.readTree(responseBody);
            Map<String, String> error = new HashMap<>() {{
                put("message", errorBody.path("message").asText());
                put("statusCode", errorBody.path("statusCode").asText());
            }};
            return ResponseEntity
                    .status(Integer.parseInt(errorBody.path("statusCode").asText()))
                    .body(error);

        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
