package com.ClaimsManagement.MemberMicroservice.Schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Claim status schema")
public class ClaimStatusSchema {
    @Schema(description = "Claim Status", example = "sanctioning")
    private String claimStatus;

    @Schema(description = "Claim status description", example = "Your claim has been acknowledged")
    private String claimStatusDescription;

    public ClaimStatusSchema(){}

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getClaimStatusDescription() {
        return claimStatusDescription;
    }

    public void setClaimStatusDescription(String claimStatusDescription) {
        this.claimStatusDescription = claimStatusDescription;
    }
}
