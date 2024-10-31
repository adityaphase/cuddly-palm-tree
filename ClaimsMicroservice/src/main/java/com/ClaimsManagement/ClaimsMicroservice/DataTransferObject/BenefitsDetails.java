package com.ClaimsManagement.ClaimsMicroservice.DataTransferObject;

import java.math.BigDecimal;

public class BenefitsDetails {
    private Long benefitsId;
    private String benefitsList;
    private BigDecimal benefitsMaxClaimable;

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

    public BigDecimal getBenefitsMaxClaimable() {
        return benefitsMaxClaimable;
    }

    public void setBenefitsMaxClaimable(BigDecimal benefitsMaxClaimable) {
        this.benefitsMaxClaimable = benefitsMaxClaimable;
    }

    public BenefitsDetails(){}
}
