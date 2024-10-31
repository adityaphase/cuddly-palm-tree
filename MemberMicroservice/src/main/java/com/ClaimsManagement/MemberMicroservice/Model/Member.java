package com.ClaimsManagement.MemberMicroservice.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="member_details")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(name="policy_id")
    private Long policyId;

    @Column(name="member_name")
    private String memberName;

    @Column(name="member_age")
    private Long memberAge;

    @Column(name="member_address")
    private String memberAddress;

    @Column(name="member_phone")
    private Long memberPhone;

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(Long memberAge) {
        this.memberAge = memberAge;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public Long getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(Long memberPhone) {
        this.memberPhone = memberPhone;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Member(){}
}
