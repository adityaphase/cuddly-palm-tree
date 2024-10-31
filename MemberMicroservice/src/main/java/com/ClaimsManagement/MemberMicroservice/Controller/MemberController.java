package com.ClaimsManagement.MemberMicroservice.Controller;

import com.ClaimsManagement.MemberMicroservice.DataTransferObject.ClaimDetailsDTO;
import com.ClaimsManagement.MemberMicroservice.Exceptions.AccessNotPermittedException;
import com.ClaimsManagement.MemberMicroservice.Model.MemberPremium;
import com.ClaimsManagement.MemberMicroservice.Schema.ClaimStatusResponseSchema;
import com.ClaimsManagement.MemberMicroservice.Schema.ClaimStatusSchema;
import com.ClaimsManagement.MemberMicroservice.Service.MemberService;
import com.ClaimsManagement.MemberMicroservice.Service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Member Microservice", description = "Member microservice API points")
@RestController
@RequestMapping("/member")
public class MemberController {
    @Value("${member.token.username}")
    private String memberUsername;
    @Value("${member.token.password}")
    private String memberPassword;

    @Autowired
    private MemberService memberService;

    private final TokenService tokenService;

    public MemberController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "View all bills for the user based on their Policy ID and Member ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all the bills",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MemberPremium.class)) }),
            @ApiResponse(responseCode = "401", description = "Token is malformed, expired or doesn't exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Tried to access forbidden content. " +
                    "Usually happens for unassigned Policy ID for this user.",
                    content = @Content) })
    @GetMapping("/viewBills/{memberId}/{policyId}")
    public ResponseEntity<List<?>> viewBills(@RequestHeader("Authorization") String authorizationHeader,
                                             @PathVariable Long memberId,
                                             @PathVariable Long policyId){

        String token = authorizationHeader.replace("Bearer ", "");
        if (tokenService.isTokenValid(token)) {
            if(!tokenService.fetchThisPolicyId().contains(policyId)){
                throw new AccessNotPermittedException("This Policy ID: " + policyId + " is not accredited with you");
            }
            return ResponseEntity.ok(memberService.fetchBills(memberId, policyId));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(List.of("Error", "The token is invalid, expired or doesn't exist"));
        }
    }

    @Operation(summary = "Fetches the claim status for a given Claim ID, Policy ID and Member ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched claim details",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClaimStatusSchema.class))}),
            @ApiResponse(responseCode = "401", description = "Token is malformed, expired or doesn't exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Tried to access forbidden content. " +
                    "Usually happens for unassigned Policy ID for this user.",
                    content = @Content) })
    @GetMapping("/getClaimStatus/{claimId}/{policyId}/{memberId}")
    public ResponseEntity<?> getClaimStatus(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable Long claimId,
                                            @PathVariable Long policyId,
                                            @PathVariable Long memberId){
        if (tokenService.isTokenValid(authorizationHeader.replace("Bearer ", ""))) {
            if(!tokenService.fetchThisPolicyId().contains(policyId)){
                throw new AccessNotPermittedException("This Policy ID: " + policyId + " is not accredited with you");
            }
            return ResponseEntity.ok(memberService.requestClaimStatus(claimId, policyId, memberId,
                    this.tokenService.generateNewToken().getToken()).getBody());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(List.of("Error", "The token is invalid, expired or doesn't exist"));
        }
    }

    @Operation(summary = "Submit a claim status for given claim details parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all the bills",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClaimStatusResponseSchema.class)) }),
            @ApiResponse(responseCode = "401", description = "Token is malformed, expired or doesn't exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Tried to access forbidden content. " +
                    "Usually happens for unassigned Policy ID for this user.",
                    content = @Content) })
    @PostMapping("/submitClaim")
    public ResponseEntity<Map<?, ?>> submitClaim(@RequestHeader("Authorization") String authToken,
                                                 @RequestBody ClaimDetailsDTO claimDetailsDTO){
        String token = "Bearer " + tokenService.generateNewToken().getToken();
        if (tokenService.isTokenValid(authToken.replace("Bearer ", ""))) {
            Long tempId = claimDetailsDTO.getPolicyId();
            if(!tokenService.fetchThisPolicyId().contains(tempId)){
                throw new AccessNotPermittedException("This Policy ID: " + tempId + " is not accredited with you");
            }
            return ResponseEntity.ok(memberService.sendSubmitClaim(token,
                    claimDetailsDTO));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new HashMap<>(){{put("Error: ", "The token is invalid, expired or doesn't exist");}});
        }
    }
}
