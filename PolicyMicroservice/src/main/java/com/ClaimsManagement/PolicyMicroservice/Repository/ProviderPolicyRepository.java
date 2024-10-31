package com.ClaimsManagement.PolicyMicroservice.Repository;

import com.ClaimsManagement.PolicyMicroservice.Model.ProviderPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderPolicyRepository extends JpaRepository<ProviderPolicy, Long> {
    @Query(value="select * from provider_policy where group_id = :groupId", nativeQuery = true)
    List<ProviderPolicy> findByGroupId(@Param("groupId") Long groupId);
}
