package com.ClaimsManagement.PolicyMicroservice.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ClaimsManagement.PolicyMicroservice.Exceptions.PolicyIdNotFoundException;
import com.ClaimsManagement.PolicyMicroservice.Model.Benefits;
import com.ClaimsManagement.PolicyMicroservice.Model.MemberPolicy;
import com.ClaimsManagement.PolicyMicroservice.Model.ProviderPolicy;
import com.ClaimsManagement.PolicyMicroservice.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class PolicyServiceTest {

    @Mock
    private ProviderPolicyRepository providerPolicyRepository;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private BenefitsRepository benefitsRepository;

    @Mock
    private MemberPolicyRepository memberPolicyRepository;

    @InjectMocks
    private PolicyService policyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchChainOfProviders() {
        Long policyId = 1123123L;
        Long groupId = 54564L;
        List<ProviderPolicy> mockProviderPolicies = List.of(new ProviderPolicy(), new ProviderPolicy());

        when(policyRepository.findGroupIdByPolicyId(policyId)).thenReturn(Optional.of(groupId));
        when(providerPolicyRepository.findByGroupId(groupId)).thenReturn(mockProviderPolicies);

        List<ProviderPolicy> result = policyService.fetchChainOfProviders(policyId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(policyRepository).findGroupIdByPolicyId(policyId);
        verify(providerPolicyRepository).findByGroupId(groupId);
    }

    @Test
    public void testFetchChainOfProviders_policyIdNotFound() {
        Long policyId = 92392L;
        when(policyRepository.findGroupIdByPolicyId(policyId)).thenReturn(Optional.empty());

        PolicyIdNotFoundException thrown = assertThrows(
                PolicyIdNotFoundException.class,
                () -> policyService.fetchChainOfProviders(policyId)
        );
        assertEquals("Error in fetching data. Please check your Policy ID.", thrown.getMessage());
    }

    @Test
    public void testFetchEligibleBenefits() {
        Long policyId = 12343L;
        Long memberId = 23242L;
        List<Long> benefitsIdList = List.of(343L, 433L);
        Benefits benefit1 = new Benefits();
        Benefits benefit2 = new Benefits();
        List<Benefits> benefits = List.of(benefit1, benefit2);

        when(memberPolicyRepository.findByMemberIdAndPolicyId(memberId, policyId)).thenReturn(benefitsIdList);
        when(benefitsRepository.findByBenefitsId(343L)).thenReturn(List.of(benefit1));
        when(benefitsRepository.findByBenefitsId(433L)).thenReturn(List.of(benefit2));

        List<List<Benefits>> result = policyService.fetchEligibleBenefits(policyId, memberId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(List.of(benefit1)));
        assertTrue(result.contains(List.of(benefit2)));
        verify(memberPolicyRepository).findByMemberIdAndPolicyId(memberId, policyId);
        verify(benefitsRepository).findByBenefitsId(343L);
        verify(benefitsRepository).findByBenefitsId(433L);
    }

    @Test
    public void testFetchEligibleClaimAmount() {
        Long memberId = 1L;
        Long policyId = 2L;
        Long benefitsId = 3L;
        BigDecimal maxClaimableMultiplier = new BigDecimal("1.5");
        BigDecimal eligibleClaimAmount = new BigDecimal("10000.00");
        BigDecimal expectedClaimAmount = maxClaimableMultiplier.multiply(eligibleClaimAmount).setScale(2, RoundingMode.HALF_UP);

        when(memberPolicyRepository.findByMemberIdAndPolicyIdAndBenefitsId(memberId, policyId, benefitsId))
                .thenReturn(Optional.of(new MemberPolicy()));
        when(benefitsRepository.findMaxClaimableByBenefitsId(benefitsId)).thenReturn(Optional.of(maxClaimableMultiplier));
        when(policyRepository.findEligibleClaimAmountByPolicyId(policyId)).thenReturn(Optional.of(eligibleClaimAmount));

        Optional<BigDecimal> result = policyService.fetchEligibleClaimAmount(memberId, policyId, benefitsId);

        assertTrue(result.isPresent());
        assertEquals(expectedClaimAmount, result.get());
        verify(memberPolicyRepository).findByMemberIdAndPolicyIdAndBenefitsId(memberId, policyId, benefitsId);
        verify(benefitsRepository).findMaxClaimableByBenefitsId(benefitsId);
        verify(policyRepository).findEligibleClaimAmountByPolicyId(policyId);
    }

}

