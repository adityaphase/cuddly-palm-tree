package com.ClaimsManagement.MemberMicroservice.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name="member_claim")
public class MemberClaim {
    @Id
    @Column(name="claim_id")
    private Long claimId;

    @Column(name="member_id")
    private Long memberId;

    @Column(name="policy_id")
    private Long policyId;

    @Column(name="claim_date", columnDefinition = "DATE")
    private Date claimDate;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
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

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public MemberClaim(){}

    public MemberClaim(Long claimId, Long memberId, Long policyId, Date claimDate) {
        this.claimId = claimId;
        this.memberId = memberId;
        this.policyId = policyId;
        this.claimDate = claimDate;
    }
}
