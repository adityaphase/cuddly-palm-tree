package com.ClaimsManagement.ClaimsMicroservice.Exceptions;


public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message){
        super(message);
    }
    public DataNotFoundException(String message, Throwable clause){
        super(message, clause);
    }
}
