package com.ClaimsManagement.MemberMicroservice.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="member_premium_details")
public class MemberPremium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_premium_id")
    private Long memberPremiumId;

    @Column(name="primary_member_id")
    private Long primaryMemberId;

    @Column(name="policy_id")
    private Long policyId;

    @Column(name="due_date", columnDefinition = "DATE")
    private Date dueDate;

    @Column(name="due_amount")
    private BigDecimal dueAmount;

    @Column(name="late_payment_charges")
    private BigDecimal latePaymentCharges;

    @Column(name="payment_status")
    private String paymentStatus;

    @Column(name="payment_date", columnDefinition = "DATE")
    private Date paymentDate;

    public Long getPrimaryMemberId() {
        return primaryMemberId;
    }

    public void setPrimaryMemberId(Long primaryMemberId) {
        this.primaryMemberId = primaryMemberId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(BigDecimal dueAmount) {
        this.dueAmount = dueAmount;
    }

    public BigDecimal getLatePaymentCharges() {
        return latePaymentCharges;
    }

    public void setLatePaymentCharges(BigDecimal latePaymentCharges) {
        this.latePaymentCharges = latePaymentCharges;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public MemberPremium(){}
}
