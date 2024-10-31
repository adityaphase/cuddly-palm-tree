package com.ClaimsManagement.MemberMicroservice.Client;

import com.ClaimsManagement.MemberMicroservice.DataTransferObject.LoginDTO;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import com.ClaimsManagement.MemberMicroservice.DataTransferObject.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="SecurityMicroservice", url="http://localhost:8085")
public interface SecurityClient {

    @PostMapping("/auth/validateToken")
    boolean validateToken(@RequestBody TokenRequest tokenRequest);

    @GetMapping("/auth/getAuthPolicyId")
    List<Long> getAuthPolicyId();

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginDTO loginDTO);


}
