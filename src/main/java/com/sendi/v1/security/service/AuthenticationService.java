package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.UserDuplicationException;
import com.sendi.v1.security.auth.AuthenticationRequest;
import com.sendi.v1.security.auth.AuthenticationResponse;
import com.sendi.v1.security.auth.RegisterRequest;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.util.ErrorMessages;
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
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private String adminPassword;

    public AuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
//        validateUsername(registerRequest);
        Role role = promoteAdminIfFirst();

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .role(role)
                .build();

        userService.createOrUpdate(user);

        return generateTokenAndAuthResponse(user);
    }

    private Role promoteAdminIfFirst() {
        if (userService.count() == 0) {
            return roleService.getByName("ADMIN");
        }

        return roleService.getByName("USER");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));

        User user = userService
                .getUserByUsername(authenticationRequest.getUsername());

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
