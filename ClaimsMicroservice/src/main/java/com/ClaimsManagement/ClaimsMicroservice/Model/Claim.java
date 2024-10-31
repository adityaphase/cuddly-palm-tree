package com.ClaimsManagement.ClaimsMicroservice.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="claim_id")
    private Long claimId;

    @Column(name = "policy_id")
    private Long policyId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "claim_status")
    private String claimStatus;

    @Column(name = "claim_status_details")
    private String claimStatusDetails;

    @Column(name = "policy_details")
    private String policyDetails;

    @Column(name = "hospital_details")
    private Long hospitalDetails;

    @Column(name = "benefits_availed")
    private Long benefitsAvailedId;

    @Column(name="billed_amount")
    private BigDecimal billedAmount;

    @Column(name = "amount_claimed")
    private BigDecimal amountClaimed;

    @Column(name = "amount_settled")
    private BigDecimal amountSettled;

    public Long getClaimId() {
        return claimId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getClaimStatusDetails() {
        return claimStatusDetails;
    }

    public void setClaimStatusDetails(String claimStatusDetails) {
        this.claimStatusDetails = claimStatusDetails;
    }

    public String getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(String policyDetails) {
        this.policyDetails = policyDetails;
    }

    public Long getHospitalDetails() {
        return hospitalDetails;
    }

    public void setHospitalDetails(Long hospitalDetails) {
        this.hospitalDetails = hospitalDetails;
    }

    public Long getBenefitsAvailedId() {
        return benefitsAvailedId;
    }

    public void setBenefitsAvailedId(Long benefitsAvailedId) {
        this.benefitsAvailedId = benefitsAvailedId;
    }

    public BigDecimal getAmountClaimed() {
        return amountClaimed;
    }

    public void setAmountClaimed(BigDecimal amountClaimed) {
        this.amountClaimed = amountClaimed;
    }

    public BigDecimal getAmountSettled() {
        return amountSettled;
    }

    public void setAmountSettled(BigDecimal amountSettled) {
        this.amountSettled = amountSettled;
    }

    public BigDecimal getBilledAmount() {
        return billedAmount;
    }

    public void setBilledAmount(BigDecimal billedAmount) {
        this.billedAmount = billedAmount;
    }

    public Claim(){}

    public Claim(Long policyId, Long memberId, String claimStatus, String claimStatusDetails, String policyDetails, Long hospitalDetails, Long benefitsAvailedId, BigDecimal billedAmount, BigDecimal amountClaimed, BigDecimal amountSettled) {
        this.policyId = policyId;
        this.memberId = memberId;
        this.claimStatus = claimStatus;
        this.claimStatusDetails = claimStatusDetails;
        this.policyDetails = policyDetails;
        this.hospitalDetails = hospitalDetails;
        this.benefitsAvailedId = benefitsAvailedId;
        this.billedAmount = billedAmount;
        this.amountClaimed = amountClaimed;
        this.amountSettled = amountSettled;
    }
}
