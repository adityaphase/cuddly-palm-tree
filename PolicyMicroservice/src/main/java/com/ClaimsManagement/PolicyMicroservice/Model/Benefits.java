package com.ClaimsManagement.PolicyMicroservice.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="benefits")
public class Benefits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="benefits_id", unique = true)
    private Long benefitsId;

    @Column(name="benefits_list")
    private String benefitsList;

    @Column(name="max_claimable", precision = 2)
    private BigDecimal maxClaimable;

    public BigDecimal getMaxClaimable() {
        return maxClaimable;
    }

    public void setMaxClaimable(BigDecimal maxClaimable) {
        this.maxClaimable = maxClaimable;
    }

    public Long getBenefitsId() {
        return benefitsId;
    }

    public void setBenefitsId(Long benefitsId) {
        this.benefitsId = benefitsId;
    }

    public String getBenefitsList() {
        return benefitsList;
    }

    public void setBenefitsList(String benefitsList) {
        this.benefitsList = benefitsList;
    }

    public Benefits(){}
}
