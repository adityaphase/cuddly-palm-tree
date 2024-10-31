package com.ClaimsManagement.ClaimsMicroservice.Service;

public enum Status {
    SANCTIONING("sanctioning"),
    REJECTING("rejecting"),
    REQUESTING_FURTHER_INFORMATION("requesting further information"),
    RAISING_A_DISPUTE("raising a dispute");

    private final String displayStatus;
    Status(String displayStatus){
        this.displayStatus = displayStatus;
    }

    @Override
    public String toString(){
        return this.displayStatus;
    }
}
