package com.ClaimsManagement.MemberMicroservice.Service;

import com.ClaimsManagement.MemberMicroservice.Client.SecurityClient;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.LoginDTO;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.LoginResponseDTO;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.TokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    @Value("${member.token.username}")
    private String memberUsername;
    @Value("${member.token.password}")
    private String memberPassword;

    private final SecurityClient securityClient;

    public TokenService(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    public boolean isTokenValid(String token) {
        return securityClient.validateToken(new TokenRequest(token, "member"));
    }

    public LoginResponseDTO generateNewToken(){
        return securityClient.authenticate(new LoginDTO().setUsername(memberUsername).setPassword(memberPassword)).getBody();
    }

    public List<Long> fetchThisPolicyId(){
        return securityClient.getAuthPolicyId();
    }
}

