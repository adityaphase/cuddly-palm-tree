package com.ClaimsManagement.MemberMicroservice.Repository;

import com.ClaimsManagement.MemberMicroservice.Model.MemberClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberClaimRepository extends JpaRepository<MemberClaim, Long> {
}
