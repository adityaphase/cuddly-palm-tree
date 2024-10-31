package com.ClaimsManagement.PolicyMicroservice.Exceptions;

public class PolicyIdNotFoundException extends RuntimeException{
    public PolicyIdNotFoundException(String message){
        super(message);
    }
    public PolicyIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
