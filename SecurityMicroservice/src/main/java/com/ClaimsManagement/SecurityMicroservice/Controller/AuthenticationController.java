package com.ClaimsManagement.SecurityMicroservice.Controller;

import com.ClaimsManagement.SecurityMicroservice.DataTransferObject.LoginDTO;
import com.ClaimsManagement.SecurityMicroservice.DataTransferObject.LoginResponseDTO;

import com.ClaimsManagement.SecurityMicroservice.DataTransferObject.TokenRequestDTO;
import com.ClaimsManagement.SecurityMicroservice.Model.User;
import com.ClaimsManagement.SecurityMicroservice.Repository.UserRepository;
import com.ClaimsManagement.SecurityMicroservice.Service.JwtService;
import com.ClaimsManagement.SecurityMicroservice.Service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private List<Long> REF_POLICY_ID;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/createDummyUser")
    public ResponseEntity<User> register(@RequestBody LoginDTO loginDTO) {
        User dummyUser = authenticationService.createDummyUser(loginDTO);
        return ResponseEntity.ok(dummyUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginDTO loginDTO) throws JsonProcessingException {
        User authenticatedUser = authenticationService.authenticate(loginDTO);
        String role = userRepository.findRoleByUsername(loginDTO.getUsername());
        jwtService.setSecretKey(role);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        if(REF_POLICY_ID == null){
            REF_POLICY_ID = authenticationService.fetchCurrentPolicyId(loginDTO.getUsername());
        }
        return ResponseEntity.ok(loginResponse);
    }

    @Value("${security.jwt.secret-key}")
    private String baseKey;

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody TokenRequestDTO tokenRequestDTO){
        String secretKey = baseKey + tokenRequestDTO.getRole();
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(tokenRequestDTO.getToken());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/getAuthPolicyId")
    public List<Long> getAuthPolicyId(){
        return REF_POLICY_ID;
    }


}