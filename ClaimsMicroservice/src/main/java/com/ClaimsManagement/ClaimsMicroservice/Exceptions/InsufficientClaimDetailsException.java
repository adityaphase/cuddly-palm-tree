package com.ClaimsManagement.ClaimsMicroservice.Exceptions;

public class InsufficientClaimDetailsException extends RuntimeException{
    public InsufficientClaimDetailsException(String message){
        super(message);
    }
    public InsufficientClaimDetailsException(String message, Throwable clause){
        super(message, clause);
    }
}
