package com.ClaimsManagement.ClaimsMicroservice.Service;

import com.ClaimsManagement.ClaimsMicroservice.Client.SecurityClient;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.LoginDTO;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.LoginResponseDTO;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.TokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${claims.token.username}")
    private String claimsUsername;
    @Value("${claims.token.password}")
    private String claimsPassword;

    private final SecurityClient securityClient;

    public TokenService(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    public boolean isTokenValid(String token) {
        return securityClient.validateToken(new TokenRequest(token, "internal"));
    }

    public LoginResponseDTO generateNewToken(){
        return securityClient.authenticate(new LoginDTO().setUsername("claims").setPassword("password")).getBody();
    }
}

