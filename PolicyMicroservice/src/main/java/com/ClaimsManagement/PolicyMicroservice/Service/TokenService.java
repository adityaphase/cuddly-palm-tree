package com.ClaimsManagement.PolicyMicroservice.Service;

import com.ClaimsManagement.PolicyMicroservice.Client.SecurityClient;
import com.ClaimsManagement.PolicyMicroservice.DataTransferObject.TokenRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final SecurityClient securityClient;

    public TokenService(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    public boolean isTokenValid(String token) {
        return securityClient.validateToken(new TokenRequest(token, "internal"));
    }
}

