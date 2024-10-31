package com.ClaimsManagement.PolicyMicroservice.Model;

import jakarta.persistence.*;

@Entity
@Table(name="member_policy")
public class MemberPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_id", referencedColumnName = "policy_id")
    private Policy policyId;

    @Column(name="member_id")
    private Long memberId;

    @Column(name="member_details")
    private String memberDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "benefits_id", referencedColumnName = "benefits_id")
    private Benefits benefitsId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(String memberDetails) {
        this.memberDetails = memberDetails;
    }

    public MemberPolicy(){}
}
