package com.ClaimsManagement.MemberMicroservice.Exceptions;

public class AccessNotPermittedException extends RuntimeException{
    public AccessNotPermittedException(String message){
        super(message);
    }
    public AccessNotPermittedException(String message, Throwable clause){
        super(message, clause);
    }
}
