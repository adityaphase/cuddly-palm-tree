package com.ClaimsManagement.PolicyMicroservice.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="policy_id", unique = true)
    private Long policyId;

    @Column(name="subscription_date", columnDefinition = "DATE")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date subscriptionDate;

    @Column(name="premium", precision = 2)
    private BigDecimal premium;

    @Column(name="tenure")
    private Long tenure;

    @Column(name="group_id")
    private Long groupId;

    @Column(name="eligible_claim_amount")
    private BigDecimal eligibleClaimAmount;

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Long getTenure() {
        return tenure;
    }

    public void setTenure(Long tenure) {
        this.tenure = tenure;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public BigDecimal getEligibleClaimAmount() {
        return eligibleClaimAmount;
    }

    public void setEligibleClaimAmount(BigDecimal eligibleClaimAmount) {
        this.eligibleClaimAmount = eligibleClaimAmount;
    }

    public Policy(){}
}
