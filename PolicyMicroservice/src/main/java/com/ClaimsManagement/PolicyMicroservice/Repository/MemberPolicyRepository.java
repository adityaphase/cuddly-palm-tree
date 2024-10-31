package com.ClaimsManagement.PolicyMicroservice.Repository;

import com.ClaimsManagement.PolicyMicroservice.Model.MemberPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberPolicyRepository extends JpaRepository<MemberPolicy, Long> {
    @Query(value = "SELECT benefits_id FROM member_policy WHERE member_id = :memberId AND policy_id = :policyId", nativeQuery = true)
    List<Long> findByMemberIdAndPolicyId(@Param("memberId") Long memberId, @Param("policyId") Long policyId);

    @Query(value = "SELECT * FROM member_policy WHERE member_id = :memberId AND policy_id = :policyId AND benefits_id = :benefitsId", nativeQuery = true)
    Optional<MemberPolicy> findByMemberIdAndPolicyIdAndBenefitsId(@Param("memberId") Long memberId,
                                                                  @Param("policyId") Long policyId,
                                                                  @Param("benefitsId") Long benefitsId);
}
