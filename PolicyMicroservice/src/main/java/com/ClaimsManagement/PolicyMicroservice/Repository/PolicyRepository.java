package com.ClaimsManagement.PolicyMicroservice.Repository;

import com.ClaimsManagement.PolicyMicroservice.Model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;


@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    @Query(value = "SELECT group_id FROM policy WHERE policy_id = :policyId", nativeQuery = true)
    Optional<Long> findGroupIdByPolicyId(@Param("policyId") Long policyId);

    @Query(value = "SELECT eligible_claim_amount FROM policy WHERE policy_id = :policyId", nativeQuery = true)
    Optional<BigDecimal> findEligibleClaimAmountByPolicyId(@Param("policyId") Long policyId);
}
