package com.ClaimsManagement.ClaimsMicroservice.Client;

import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.BenefitsDetails;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.HospitalDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@FeignClient(name="PolicyMicroservice", url = "http://localhost:8084")
public interface PolicyClient {
    @GetMapping("/policy/getChainOfProviders/{policyId}")
    public ResponseEntity<List<HospitalDetails>> getChainOfProviders(@RequestHeader("Authorization") String authToken,
                                                                     @PathVariable Long policyId);

    @GetMapping("/policy/getEligibleBenefits/{policyId}/{memberId}")
    public ResponseEntity<List<List<BenefitsDetails>>> getEligibleBenefits(@RequestHeader("Authorization") String authToken,
                                                                           @PathVariable Long policyId,
                                                                           @PathVariable Long memberId);

    @GetMapping("/policy/getEligibleClaimAmount/{policyId}/{memberId}/{benefitsId}")
    public ResponseEntity<Optional<BigDecimal>> getEligibleClaimAmount(@RequestHeader("Authorization") String authToken,
                                                                       @PathVariable Long policyId,
                                                                       @PathVariable Long memberId,
                                                                       @PathVariable Long benefitsId);
}
