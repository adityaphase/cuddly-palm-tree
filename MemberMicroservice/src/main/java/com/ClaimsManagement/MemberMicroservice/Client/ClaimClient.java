package com.ClaimsManagement.MemberMicroservice.Client;

import com.ClaimsManagement.MemberMicroservice.DataTransferObject.ClaimDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@FeignClient(name="ClaimsMicroservice", url = "http://localhost:8083")
public interface ClaimClient {
    @GetMapping("claim/getClaimStatus/{claimId}/{policyId}/{memberId}")
    public ResponseEntity<HashMap<?,?>> getClaimStatus(@RequestHeader("Authorization") String token, @PathVariable Long claimId, @PathVariable Long policyId, @PathVariable Long memberId);

    @PostMapping("claim/submitClaim")
    public ResponseEntity<Map<String, Object>> submitClaim(@RequestHeader("Authorization") String authToken,
            @RequestBody ClaimDetailsDTO claimDetailsDTO);
}
