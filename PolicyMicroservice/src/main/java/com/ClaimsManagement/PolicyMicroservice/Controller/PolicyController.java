package com.ClaimsManagement.PolicyMicroservice.Controller;

import com.ClaimsManagement.PolicyMicroservice.Exceptions.MalformedTokenException;
import com.ClaimsManagement.PolicyMicroservice.Exceptions.PolicyIdNotFoundException;
import com.ClaimsManagement.PolicyMicroservice.Model.Benefits;
import com.ClaimsManagement.PolicyMicroservice.Model.ProviderPolicy;
import com.ClaimsManagement.PolicyMicroservice.Service.PolicyService;
import com.ClaimsManagement.PolicyMicroservice.Service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/policy")
public class PolicyController {
    private final PolicyService policyService;
    private final TokenService tokenService;

    public PolicyController(PolicyService policyService, TokenService tokenService){
        this.policyService = policyService;
        this.tokenService = tokenService;
    }

    @GetMapping("/getChainOfProviders/{policyId}")
    public ResponseEntity<List<ProviderPolicy>> getChainOfProviders(@RequestHeader("Authorization") String authToken,
                                                                    @PathVariable Long policyId){
        if (tokenService.isTokenValid(authToken.replace("Bearer ", ""))) {
            return ResponseEntity.ok(policyService.fetchChainOfProviders(policyId));
        } else {
            throw new MalformedTokenException("Policy Microservice Error: Token may be malformed or expired or Invalid");
        }
    }

    @GetMapping("/getEligibleBenefits/{policyId}/{memberId}")
    public ResponseEntity<List<List<Benefits>>> getEligibleBenefits(@RequestHeader("Authorization") String authToken,
                                                                    @PathVariable Long policyId,
                                                                    @PathVariable Long memberId){
        if (tokenService.isTokenValid(authToken.replace("Bearer ", ""))) {
            return ResponseEntity.ok(policyService.fetchEligibleBenefits(policyId, memberId));
        } else {
            throw new MalformedTokenException("Policy Microservice Error: Token may be malformed or expired or Invalid");
        }
    }

    @GetMapping("/getEligibleClaimAmount/{policyId}/{memberId}/{benefitsId}")
    public ResponseEntity<?> getEligibleClaimAmount(@RequestHeader("Authorization") String authToken,
                                                    @PathVariable Long policyId,
                                                    @PathVariable Long memberId,
                                                    @PathVariable Long benefitsId){
        if (tokenService.isTokenValid(authToken.replace("Bearer ", ""))) {
            return ResponseEntity.ok(policyService.fetchEligibleClaimAmount(memberId, policyId, benefitsId));
        } else {
            throw new MalformedTokenException("Policy Microservice Error: Token may be malformed or expired or Invalid");
        }
    }
}
