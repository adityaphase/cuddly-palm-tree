package com.ClaimsManagement.ClaimsMicroservice.Repository;

import com.ClaimsManagement.ClaimsMicroservice.Model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    @Query(value = "SELECT claim_status, claim_status_details FROM claims WHERE claim_id = :claimId AND policy_id = :policyId AND member_id = :memberId", nativeQuery = true)
    Optional<List<String>> findStatusRemarksByClaimIdAndPolicyIdAndMemberId(@Param("claimId") Long claimId,
                                                                       @Param("policyId") Long policyId,
                                                                       @Param("memberId") Long memberId);
}
