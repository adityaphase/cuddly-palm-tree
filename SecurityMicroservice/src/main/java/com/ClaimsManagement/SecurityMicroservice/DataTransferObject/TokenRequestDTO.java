package com.ClaimsManagement.SecurityMicroservice.DataTransferObject;

public class TokenRequestDTO {
    private String token;
    private String role;

    public TokenRequestDTO() {
    }

    public TokenRequestDTO(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
