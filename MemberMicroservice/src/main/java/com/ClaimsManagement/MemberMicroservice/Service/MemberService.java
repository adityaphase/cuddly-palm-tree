package com.ClaimsManagement.MemberMicroservice.Service;

import com.ClaimsManagement.MemberMicroservice.Client.ClaimClient;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.ClaimDetailsDTO;
import com.ClaimsManagement.MemberMicroservice.Model.MemberClaim;
import com.ClaimsManagement.MemberMicroservice.Repository.MemberClaimRepository;
import com.ClaimsManagement.MemberMicroservice.Repository.MemberPremiumRepository;
import com.ClaimsManagement.MemberMicroservice.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    @Autowired
    private ClaimClient claimClient;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberClaimRepository memberClaimRepository;
    @Autowired
    private MemberPremiumRepository memberPremiumRepository;

    public ResponseEntity<HashMap<?, ?>> requestClaimStatus(Long claimId, Long policyId, Long memberId, String token){
        return ResponseEntity.ok(claimClient.getClaimStatus("Bearer " + token, claimId, policyId, memberId)).getBody();
    }

    public Map<String, Object> sendSubmitClaim(String token, ClaimDetailsDTO claimDetailsDTO){
        Map<String, Object> responseClaim =  claimClient.submitClaim(token, claimDetailsDTO).getBody();
        Long claimIdTemp = Long.parseLong(responseClaim.get("Current claim id:").toString());
        memberClaimRepository.save(new MemberClaim(claimIdTemp,
                claimDetailsDTO.getMemberId(),
                claimDetailsDTO.getPolicyId(),
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        return responseClaim;
    }

    @Cacheable(value = "fetchBillsCache", key = "#memberId + '-' + #policyId")
    public List<?> fetchBills(Long memberId, Long policyId){
        return memberPremiumRepository.findByPolicyIdAndMemberId(policyId, memberId);
    }

}
