package com.ClaimsManagement.SecurityMicroservice.Exception;

import com.ClaimsManagement.SecurityMicroservice.Service.ErrorResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
        ErrorResponseService errorResponseService =
                new ErrorResponseService(ex.getMessage() + ": The username or password is incorrect"
                , HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponseService);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
        ErrorResponseService errorResponseService =
                new ErrorResponseService(ex.getMessage() + ": You are not authorized to access this resource",
                        HttpStatus.FORBIDDEN.value());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponseService);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex){
        ErrorResponseService errorResponseService =
                new ErrorResponseService((ex.getMessage() + ": The JWT signature is invalid"),
                        HttpStatus.FORBIDDEN.value());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponseService);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleSignatureException(ExpiredJwtException ex){
        ErrorResponseService errorResponseService =
                new ErrorResponseService((ex.getMessage() + ": The JWT token has expired"),
                        HttpStatus.FORBIDDEN.value());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponseService);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred during data fetch");
    }
}
