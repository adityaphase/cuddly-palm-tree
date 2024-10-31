package com.ClaimsManagement.PolicyMicroservice.Repository;

import com.ClaimsManagement.PolicyMicroservice.Model.Benefits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitsRepository extends JpaRepository<Benefits, Long> {
    @Query(value = "SELECT * FROM benefits WHERE benefits_id = :benefitsId", nativeQuery = true)
    List<Benefits> findByBenefitsId(@Param("benefitsId") Long benefitsId);

    @Query(value = "SELECT max_claimable FROM benefits WHERE benefits_id = :benefitsId", nativeQuery = true)
    Optional<BigDecimal> findMaxClaimableByBenefitsId(@Param("benefitsId") Long benefitsId);
}
