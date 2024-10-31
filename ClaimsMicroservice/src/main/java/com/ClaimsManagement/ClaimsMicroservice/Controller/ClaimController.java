package com.ClaimsManagement.ClaimsMicroservice.Controller;

import com.ClaimsManagement.ClaimsMicroservice.Client.SecurityClient;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.ClaimDetails;
import com.ClaimsManagement.ClaimsMicroservice.Exceptions.DataNotFoundException;
import com.ClaimsManagement.ClaimsMicroservice.Service.ClaimService;
import com.ClaimsManagement.ClaimsMicroservice.Service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/claim")
public class ClaimController {
    private ClaimService claimService;
    private TokenService tokenService;

    public ClaimController(ClaimService claimService, TokenService tokenService) {
        this.claimService = claimService;
        this.tokenService = tokenService;
    }

    @GetMapping("/getClaimStatus/{claimId}/{policyId}/{memberId}")
    public ResponseEntity<HashMap<?,?>> getClaimStatus(@RequestHeader("Authorization") String token,
                                                       @PathVariable Long claimId,
                                                       @PathVariable Long policyId,
                                                       @PathVariable Long memberId){
        if(tokenService.isTokenValid(token.replace("Bearer ", ""))){
            return ResponseEntity.ok(claimService.fetchClaimStatus(claimId, policyId, memberId));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new HashMap<>(){{
                        put("Claims Microservice Internal token error: ",
                                "The token is invalid or has expired");
                    }});
        }
    }

    @PostMapping("/submitClaim")
    public ResponseEntity<Map<?,?>> submitClaim(@RequestHeader("Authorization") String authToken,
                                                @RequestBody ClaimDetails claimDetails){
        if (tokenService.isTokenValid(authToken.replace("Bearer ", ""))) {
            return ResponseEntity.ok(claimService.checkClaim(claimDetails,"Bearer " +
                    tokenService.generateNewToken().getToken()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new HashMap<>(){{put("Error: ", "The token is invalid, expired or doesn't exist");}});
        }
    }

}
