package com.ClaimsManagement.ClaimsMicroservice.Client;

import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.LoginDTO;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.LoginResponseDTO;
import com.ClaimsManagement.ClaimsMicroservice.DataTransferObject.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="SecurityMicroservice", url="http://localhost:8085")
public interface SecurityClient {

    @PostMapping("/auth/validateToken")
    boolean validateToken(@RequestBody TokenRequest tokenRequest);

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginDTO loginDTO);
}
