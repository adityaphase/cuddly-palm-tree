package com.ClaimsManagement.MemberMicroservice.Service;

import com.ClaimsManagement.MemberMicroservice.Client.ClaimClient;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.ClaimDetailsDTO;
import com.ClaimsManagement.MemberMicroservice.Model.MemberClaim;
import com.ClaimsManagement.MemberMicroservice.Model.MemberPremium;
import com.ClaimsManagement.MemberMicroservice.Repository.MemberClaimRepository;
import com.ClaimsManagement.MemberMicroservice.Repository.MemberPremiumRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@SpringBootTest
@SpringJUnitConfig
public class MemberServiceTest {
    @Mock
    private MemberPremiumRepository memberPremiumRepository;
    @Mock
    private MemberClaimRepository memberClaimRepository;
    @Mock
    private ClaimClient claimClient;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchBills(){
        Long policyId = 165222L;
        Long memberId = 19001L;
        MemberPremium mockObj = new MemberPremium();
        mockObj.setDueAmount(BigDecimal.valueOf(600.00));
        mockObj.setDueDate(new Date());
        mockObj.setPolicyId(policyId);
        mockObj.setPrimaryMemberId(memberId);
        mockObj.setLatePaymentCharges(BigDecimal.ONE);
        mockObj.setPaymentStatus("paid");
        mockObj.setPaymentDate(null);
        List<MemberPremium> expectedBills = Arrays.asList(mockObj);

        when(memberPremiumRepository.findByPolicyIdAndMemberId(policyId, memberId)).thenReturn(expectedBills);

        List<?> actualBills = memberService.fetchBills(memberId, policyId);

        assertNotNull(actualBills);
        assertEquals(expectedBills.size(), actualBills.size());
        assertTrue(actualBills.containsAll(expectedBills));
        verify(memberPremiumRepository).findByPolicyIdAndMemberId(policyId, memberId);
    }

    @Test
    public void testSendSubmitClaim(){
        Long claimIdTemp = 11212L;
        Long memberId = 15002L;
        Long policyId = 182825L;
        String token = "test_token";
        ClaimDetailsDTO claimDetailsDTO = new ClaimDetailsDTO();
        claimDetailsDTO.setMemberId(memberId);
        claimDetailsDTO.setPolicyId(policyId);

        MemberClaim memberClaim = new MemberClaim();
        memberClaim.setClaimId(claimIdTemp);
        memberClaim.setMemberId(memberId);
        memberClaim.setPolicyId(policyId);
        memberClaim.setClaimDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Map<String, Object> claimsMap = new HashMap<>(){{
            put("Claim status: ", "sanctioning");
            put("Claim status description: ", "Your claim has been acknowledged, pending for approval");
            put("Current claim id:", 11212L);
        }};

        when(claimClient.submitClaim(eq(token), any(ClaimDetailsDTO.class)))
                .thenReturn(ResponseEntity.ok(claimsMap));

        when(memberClaimRepository.save(new MemberClaim(claimIdTemp,
                        claimDetailsDTO.getMemberId(),
                        claimDetailsDTO.getPolicyId(),
                        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())))
        ).thenReturn(memberClaim);

        Map<String, Object> claimResponse = memberService.sendSubmitClaim(token, claimDetailsDTO);

        assertEquals(claimsMap.size(), claimResponse.size());
        assertTrue(claimResponse.containsKey("Claim status: "));
        assertTrue(claimResponse.containsValue(claimIdTemp));
    }

    @Test
    public void testRequestClaimStatus(){
        String token = "mock_token";
        Long claimId = 10293012L;
        Long policyId = 234234L;
        Long memberId = 98232398L;

        HashMap<String, String> responseMap = new HashMap<>(){{
            put("Claim status", "rejecting");
            put("Claim status description", "Your policy claim has been rejected due to exceeding your policy benefit claim limit");
        }};

        when(claimClient.getClaimStatus(anyString(), anyLong(), anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok(responseMap));

        HashMap<?, ?> response = memberService.requestClaimStatus(claimId, policyId, memberId, token).getBody();

        assertEquals(response.size(), responseMap.size());
        assertTrue(response.containsKey("Claim status"));
        assertTrue(response.containsKey("Claim status description"));
    }
}
