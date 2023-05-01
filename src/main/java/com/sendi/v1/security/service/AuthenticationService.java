package com.sendi.v1.security.service;

import com.sendi.v1.security.auth.AuthenticationRequest;
import com.sendi.v1.security.auth.AuthenticationResponse;
import com.sendi.v1.security.auth.RegisterRequest;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.RoleRepository;
import com.sendi.v1.security.service.JwtService;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    private String adminPassword;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (usernameExists(registerRequest)) {
            throw new RuntimeException("Username is taken: " + registerRequest.getUsername());
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .role(getRole("USER"))
                .build();

        log.info("user is being saved ");

        userRepository.save(user);

        log.info("user is saved ");

        return generateTokenAndAuthResponse(user);
    }

    private Role getRole(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role doesn't exist in the database");
        }
        return optionalRole.get();
    }

    private boolean usernameExists(RegisterRequest registerRequest) {
        return userRepository.existsByUsername(registerRequest.getUsername());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));

        log.info("authentication = {}", auth);

        User user = userRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow();

        return generateTokenAndAuthResponse(user);
    }

    private AuthenticationResponse generateTokenAndAuthResponse(User user) {
        String jwtToken = jwtService.generateToken(user);

        log.info("JwtToken while building a response = {}", jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }
}
