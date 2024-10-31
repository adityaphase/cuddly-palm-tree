package com.ClaimsManagement.ClaimsMicroservice.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import com.ClaimsManagement.ClaimsMicroservice.Client.PolicyClient;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.BenefitsDetails;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.ClaimDetails;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.HospitalDetails;
import com.ClaimsManagement.ClaimsMicroservice.Model.Claim;
import com.ClaimsManagement.ClaimsMicroservice.Repository.ClaimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
public class ClaimsServiceTest{
    @Mock
    private PolicyClient policyClient;
    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ClaimService claimService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFetchClaimStatus() {
        Long claimId = 1L;
        Long policyId = 1L;
        Long memberId = 1L;
        List<String> claimData = List.of("Approved,Claim is approved");
        Optional<List<String>> optionalClaimData = Optional.of(claimData);

        when(claimRepository.findStatusRemarksByClaimIdAndPolicyIdAndMemberId(claimId, policyId, memberId))
                .thenReturn(optionalClaimData);

        HashMap<String, String> result = claimService.fetchClaimStatus(claimId, policyId, memberId);

        assertNotNull(result);
        assertEquals("Approved", result.get("Claim status"));
        assertEquals("Claim is approved", result.get("Claim status description"));
        verify(claimRepository, times(1))
                .findStatusRemarksByClaimIdAndPolicyIdAndMemberId(claimId, policyId, memberId);
    }

    @Test
    public void testCheckClaim(){

        Long policyId = 111L;
        Long memberId = 12312L;
        Long hospitalId = 123123L;
        Long benefitsAvailed = 12321L;
        BigDecimal totalBilledAmount = BigDecimal.valueOf(1200.22);
        BigDecimal totalClaimedAmount = BigDecimal.valueOf(1100.12);
        Long benefitsId = 111L;
        String token = "mock_token";
        Optional<BigDecimal> mockAmount = Optional.of(BigDecimal.valueOf(2000));

        BenefitsDetails benefitsDetails = new BenefitsDetails();
        benefitsDetails.setBenefitsId(benefitsId);
        benefitsDetails.setBenefitsList("some benefits");
        benefitsDetails.setBenefitsMaxClaimable(BigDecimal.valueOf(3000));

        List<List<BenefitsDetails>> mockBenefitsList = new ArrayList<>();
        List<BenefitsDetails> temp = new ArrayList<>();
        temp.add(benefitsDetails);
        mockBenefitsList.add(temp);
        ResponseEntity<List<List<BenefitsDetails>>> mockBenefitsListResponse = ResponseEntity.ok(mockBenefitsList);

        List<HospitalDetails> hospitalDetails = new ArrayList<>();
        HospitalDetails tempHospital = new HospitalDetails();
        tempHospital.setHospitalName("hospital name cool");
        tempHospital.setGroupId(121313L);
        tempHospital.setLocation("location 101");
        tempHospital.setHospitalId(1212323L);
        hospitalDetails.add(tempHospital);
        ResponseEntity<List<HospitalDetails>> hospitalDetailsResponse = ResponseEntity.ok(hospitalDetails);

        ClaimDetails claimDetails = new ClaimDetails();
        claimDetails.setPolicyId(policyId);
        claimDetails.setMemberId(memberId);
        claimDetails.setHospitalId(hospitalId);
        claimDetails.setBenefitsAvailed(benefitsAvailed);
        claimDetails.setTotalBilledAmount(totalBilledAmount);
        claimDetails.setTotalClaimedAmount(totalClaimedAmount);

        Claim claim = new Claim(claimDetails.getPolicyId(),
                claimDetails.getMemberId(),
                "claimStatus",
                "claimStatusDetails",
                "policyDetails",
                claimDetails.getHospitalId(),
                claimDetails.getBenefitsAvailed(),
                claimDetails.getTotalBilledAmount(),
                claimDetails.getTotalClaimedAmount(),
                BigDecimal.ZERO);

        when(claimRepository.save(Mockito.any(Claim.class)))
                .thenReturn(claim);
        when(policyClient.getEligibleClaimAmount(eq(token), anyLong(), anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok(mockAmount));
        when(policyClient.getEligibleBenefits(token, policyId, memberId))
                .thenReturn(mockBenefitsListResponse);
        when(policyClient.getChainOfProviders(token, policyId))
                .thenReturn(hospitalDetailsResponse);


        Map<String, Object> response = claimService.checkClaim(claimDetails, token);

        assertNotNull(response);
        verify(claimRepository).save(Mockito.any(Claim.class));
        assertEquals(response.get("Claim status: "), claim.getClaimStatus());
        assertEquals(response.get("Claim status description: "), claim.getClaimStatusDetails());
        assertEquals(response.get("Current claim id:"), claim.getClaimId());
    }
}
