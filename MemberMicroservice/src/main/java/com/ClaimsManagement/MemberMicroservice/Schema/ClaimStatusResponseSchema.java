package com.ClaimsManagement.MemberMicroservice.Schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object for the claim status")
public class ClaimStatusResponseSchema {

    @Schema(description = "The status of the claim", example = "sanctioning")
    private String claimStatus;

    @Schema(description = "A description of the claim status", example = "Your claim has been acknowledged, pending for approval")
    private String claimStatusDescription;

    @Schema(description = "The unique ID of the current claim", example = "55")
    private Integer currentClaimId;

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

    public Integer getCurrentClaimId() {
        return currentClaimId;
    }

    public void setCurrentClaimId(Integer currentClaimId) {
        this.currentClaimId = currentClaimId;
    }
}
