package com.ClaimsManagement.MemberMicroservice.Repository;

import com.ClaimsManagement.MemberMicroservice.Model.MemberPremium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface MemberPremiumRepository extends JpaRepository<MemberPremium, Long> {
    @Query(value = "SELECT * FROM member_premium_details WHERE policy_id = :policyId AND primary_member_id = :memberId", nativeQuery = true)
    List<MemberPremium> findByPolicyIdAndMemberId(@PathVariable Long policyId, @PathVariable Long memberId);
}
