package com.ClaimsManagement.PolicyMicroservice.Client;

import com.ClaimsManagement.PolicyMicroservice.DataTransferObject.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="SecurityMicroservice", url="http://localhost:8085")
public interface SecurityClient {

    @PostMapping("/auth/validateToken")
    boolean validateToken(@RequestBody TokenRequest tokenRequest);
}
