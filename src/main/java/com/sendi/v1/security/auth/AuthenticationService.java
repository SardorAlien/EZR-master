package com.sendi.v1.security.auth;

import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.jwt.JwtService;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
//                .role(Role.builder().name("USER").build())
                .build();

        log.info("user is being saved ");

        userRepository.save(user);

        log.info("user is saved ");

        return generateTokenAndAuthResponse(user);
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
