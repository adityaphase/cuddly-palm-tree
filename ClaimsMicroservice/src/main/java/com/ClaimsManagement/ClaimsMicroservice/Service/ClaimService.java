package com.ClaimsManagement.ClaimsMicroservice.Service;

import com.ClaimsManagement.ClaimsMicroservice.Client.PolicyClient;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.BenefitsDetails;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.ClaimDetails;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.HospitalDetails;
import com.ClaimsManagement.ClaimsMicroservice.Exceptions.DataNotFoundException;
import com.ClaimsManagement.ClaimsMicroservice.Exceptions.InsufficientClaimDetailsException;
import com.ClaimsManagement.ClaimsMicroservice.Model.Claim;
import com.ClaimsManagement.ClaimsMicroservice.Repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private PolicyClient policyClient;
    private BenefitsDetails benefitsDetails;


    public HashMap<String, String> fetchClaimStatus(Long claimId, Long policyId, Long memberId){
        List<String> claimData = claimRepository.findStatusRemarksByClaimIdAndPolicyIdAndMemberId(claimId, policyId, memberId)
                .orElse(new ArrayList<>());
        claimData = Arrays.stream(claimData.get(0).split(",")).toList();
        if(claimData.isEmpty()){
            throw new DataNotFoundException("No results found");
        }
        HashMap<String, String> parsedClaimData = new HashMap<>();
        parsedClaimData.put("Claim status", claimData.get(0));
        parsedClaimData.put("Claim status description", claimData.get(1));
        return parsedClaimData;
    }

    private BigDecimal fetchClaimableAmount(Long policyId, Long memberId, Long benefitsId, String token){
        Optional<BigDecimal> amt = policyClient.getEligibleClaimAmount(token, policyId, memberId, benefitsId).getBody();
        if(amt == null){
            return null;
        }
        return amt.orElse(BigDecimal.ZERO);
    }

    private static <T, R> List<R> parseDoubleList(List<List<T>> inputList, Function<T, R> idExtract){
        return inputList.stream()
                .flatMap(List::stream)
                .map(idExtract)
                .collect(Collectors.toList());
    }

    private static <T, R> List<R> parseSingleList(List<T> inputList, Function<T, R> idExtract){
        return inputList.stream()
                .map(idExtract)
                .collect(Collectors.toList());
    }

    private boolean checkForNullProperty(Object obj){
        if(obj == null) return true;
        for(Field field : obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try{
                if(field.get(obj) == null){
                    return true;
                }
            }
            catch (IllegalAccessException ex){
               ex.printStackTrace();
            }
        }
        return false;
    }

    private Claim createClaimEntity(ClaimDetails claimDetails,
                                    String claimStatus,
                                    String claimStatusDetails,
                                    String policyDetails,
                                    Optional<BigDecimal> amountSettled){

        Claim claim = new Claim(claimDetails.getPolicyId(),
                claimDetails.getMemberId(),
                claimStatus,
                claimStatusDetails,
                policyDetails,
                claimDetails.getHospitalId(),
                claimDetails.getBenefitsAvailed(),
                claimDetails.getTotalBilledAmount(),
                claimDetails.getTotalClaimedAmount(),
                amountSettled.isPresent() ? amountSettled.get() : BigDecimal.ZERO);
        return claim;
    }

    private Map<String,Object> parseResponseBody(Claim savedClaim){
        Map<String, Object> responseBody = new HashMap<>() {{
            put("Current claim id:",savedClaim.getClaimId());
            put("Claim status: ", savedClaim.getClaimStatus());
            put("Claim status description: ", savedClaim.getClaimStatusDetails());
        }};
        return responseBody;
    }


    private List<Long> fetchClaimableBenefitsIdList(Long policyId, Long memberId, String token){
       List<List<BenefitsDetails>> benefitsList =  policyClient.getEligibleBenefits(token, policyId, memberId).getBody();
        if(benefitsList == null){
            return null;
        }
        return parseDoubleList(benefitsList, BenefitsDetails::getBenefitsId);
    }

    private List<Long> fetchHospitalIdList(Long policyId, String token){
        List<HospitalDetails> hospitalList = policyClient.getChainOfProviders(token, policyId).getBody();
        if(hospitalList == null){
            throw new DataNotFoundException("No Hospitals found for policy ID: " + policyId
                    + "\n Policy ID may be incorrect");
        }
        return parseSingleList(hospitalList, HospitalDetails::getHospitalId);
    }

    public Map<String, Object> checkClaim(ClaimDetails claimDetails, String token){

        if(checkForNullProperty(claimDetails)){
            throw new InsufficientClaimDetailsException("Error: Claim details data may me malformed.");
        }

        BigDecimal claimableAmount = fetchClaimableAmount(claimDetails.getPolicyId(),
                claimDetails.getMemberId(), claimDetails.getBenefitsAvailed(), token);

        List<Long> benefitsIdList = fetchClaimableBenefitsIdList(claimDetails.getPolicyId(),
                claimDetails.getMemberId(), token);

        List<Long> hospitalIdList = fetchHospitalIdList(claimDetails.getPolicyId(), token);

        if(claimableAmount == null){
            throw new DataNotFoundException("Failed to fetch Claimable amount for current policy");
        } else if (benefitsIdList == null){
            throw new DataNotFoundException("Failed to fetch Benefits for current policy");
        } else if (hospitalIdList == null){
            throw new DataNotFoundException("Failed to fetch eligible hospitals for current policy");
        } else {
            Boolean amount = true;
            Boolean benefit = true;
            Boolean hospital = true;

            if(claimDetails.getTotalClaimedAmount().compareTo(claimableAmount) > 0){
                amount = false;
            }
            if(!benefitsIdList.contains(claimDetails.getBenefitsAvailed())){
                benefit = false;
            }
            if(!hospitalIdList.contains(claimDetails.getHospitalId())){
                hospital = false;
            }

            if(amount && benefit && hospital){
                if(claimDetails.getTotalBilledAmount().compareTo(claimDetails.getTotalClaimedAmount()) < 0){
                    return parseResponseBody(claimRepository.save(createClaimEntity(claimDetails,
                            Status.RAISING_A_DISPUTE.toString(),
                            "Claimed amount higher than billed amount, dispute raised",
                            "Some policy details",
                            Optional.empty())));
                }
                return parseResponseBody(claimRepository.save(createClaimEntity(claimDetails,
                        Status.SANCTIONING.toString(),
                        "Your claim has been acknowledged, pending for approval",
                        "Some policy details", Optional.of(claimDetails.getTotalBilledAmount()))));
            } else if (amount || benefit && hospital){
                if(!amount){
                    return parseResponseBody(claimRepository.save(createClaimEntity(claimDetails,
                            Status.REJECTING.toString(),
                            "Your policy claim has been rejected due to exceeding your policy benefit claim limit",
                            "policy details", Optional.empty())));
                }
                return parseResponseBody(claimRepository.save(createClaimEntity(claimDetails,
                        Status.REQUESTING_FURTHER_INFORMATION.toString(),
                        "Contact customer care to request avail the right benefit for your policy",
                        "See policy details", Optional.empty())));
            } else {
                return parseResponseBody(claimRepository.save(createClaimEntity(claimDetails,
                        Status.REJECTING.toString(),
                        "Your policy claim has been rejected due to overstepping policy constraints",
                        "policy details", Optional.empty())));
            }
        }
    }
}
