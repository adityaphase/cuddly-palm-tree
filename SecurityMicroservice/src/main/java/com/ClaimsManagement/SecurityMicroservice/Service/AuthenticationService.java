package com.ClaimsManagement.SecurityMicroservice.Service;

import com.ClaimsManagement.SecurityMicroservice.DataTransferObject.LoginDTO;
import com.ClaimsManagement.SecurityMicroservice.Model.User;
import com.ClaimsManagement.SecurityMicroservice.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createDummyUser(LoginDTO input) {
        User user = new User()
                .setUsername(input.getUsername())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginDTO input) {
        Object s = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword()
            )
        );

        return userRepository.findByUsername(input.getUsername()).orElseThrow();
    }

    public List<Long> fetchCurrentPolicyId(String username) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString =  userRepository.findPolicyIdByUsername(username);
        return objectMapper.readValue(jsonString, new TypeReference<List<Long>>(){});

    }
}
