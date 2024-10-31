package com.ClaimsManagement.ClaimsMicroservice.DataTransferObject;

import java.math.BigDecimal;

public class ClaimDetails {
    private Long policyId;
    private Long memberId;
    private Long hospitalId;
    private Long benefitsAvailed;
    private BigDecimal totalBilledAmount;
    private BigDecimal totalClaimedAmount;

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getBenefitsAvailed() {
        return benefitsAvailed;
    }

    public void setBenefitsAvailed(Long benefitsAvailed) {
        this.benefitsAvailed = benefitsAvailed;
    }

    public BigDecimal getTotalBilledAmount() {
        return totalBilledAmount;
    }

    public void setTotalBilledAmount(BigDecimal totalBilledAmount) {
        this.totalBilledAmount = totalBilledAmount;
    }

    public BigDecimal getTotalClaimedAmount() {
        return totalClaimedAmount;
    }

    public void setTotalClaimedAmount(BigDecimal totalClaimedAmount) {
        this.totalClaimedAmount = totalClaimedAmount;
    }

    public ClaimDetails(){}
}
