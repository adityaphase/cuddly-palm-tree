package com.ClaimsManagement.PolicyMicroservice.Service;

import com.ClaimsManagement.PolicyMicroservice.Exceptions.PolicyIdNotFoundException;
import com.ClaimsManagement.PolicyMicroservice.Model.Benefits;
import com.ClaimsManagement.PolicyMicroservice.Model.ProviderPolicy;
import com.ClaimsManagement.PolicyMicroservice.Repository.BenefitsRepository;
import com.ClaimsManagement.PolicyMicroservice.Repository.MemberPolicyRepository;
import com.ClaimsManagement.PolicyMicroservice.Repository.PolicyRepository;
import com.ClaimsManagement.PolicyMicroservice.Repository.ProviderPolicyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {
    private final ProviderPolicyRepository providerPolicyRepository;
    private final PolicyRepository policyRepository;
    private final BenefitsRepository benefitsRepository;
    private final MemberPolicyRepository memberPolicyRepository;

    public PolicyService(ProviderPolicyRepository providerPolicyRepository,
                         PolicyRepository policyRepository,
                         BenefitsRepository benefitsRepository,
                         MemberPolicyRepository memberPolicyRepository){
        this.providerPolicyRepository = providerPolicyRepository;
        this.policyRepository = policyRepository;
        this.benefitsRepository = benefitsRepository;
        this.memberPolicyRepository = memberPolicyRepository;
    }

    public List<ProviderPolicy> fetchChainOfProviders(Long policyId){
        Optional<Long> groupId = policyRepository.findGroupIdByPolicyId(policyId);
        if(groupId.isEmpty()){
            throw new PolicyIdNotFoundException("Error in fetching data. Please check your Policy ID.");
        }
        return providerPolicyRepository.findByGroupId(groupId.orElse(-1L));
    }

    public List<List<Benefits>> fetchEligibleBenefits(Long policyId, Long memberId){
        List<Long> benefitsIdList= new ArrayList<>(memberPolicyRepository.findByMemberIdAndPolicyId(memberId, policyId));
        List<List<Benefits>> benefitsList = benefitsIdList.stream()
                .map(benefitsRepository::findByBenefitsId).distinct().toList();
        return benefitsList;
    }

    public Optional<BigDecimal> fetchEligibleClaimAmount(Long memberId, Long policyId, Long benefitsId){
        if(memberPolicyRepository.findByMemberIdAndPolicyIdAndBenefitsId(memberId, policyId, benefitsId).isPresent()){
            BigDecimal maxClaimableMultiplier = benefitsRepository.findMaxClaimableByBenefitsId(benefitsId).orElse(BigDecimal.ZERO);
            BigDecimal eligibleClaimAmount = policyRepository.findEligibleClaimAmountByPolicyId(policyId).orElse(BigDecimal.ZERO);

            return Optional.of(maxClaimableMultiplier.multiply(eligibleClaimAmount).setScale(2, RoundingMode.HALF_UP));
        } else {
            return Optional.empty();
        }
    }
}
