package com.sendi.v1.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendi.v1.exception.custom.UserDuplicationException;
import com.sendi.v1.security.auth.AuthenticationRequest;
import com.sendi.v1.security.auth.AuthenticationResponse;
import com.sendi.v1.security.auth.RegisterRequest;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.security.token.Token;
import com.sendi.v1.security.token.TokenRepository;
import com.sendi.v1.security.token.TokenType;
import com.sendi.v1.util.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    private String adminPassword;

    private static final String TOKEN_PREFIX = "Bearer ";

    public AuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
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
                .getUser(authenticationRequest.getUsername());
        log.info("User in authenticate method: {}", user.toString());

        revokeAllUserTokens(user);

        return generateTokenAndAuthResponse(user);
    }

    private AuthenticationResponse generateTokenAndAuthResponse(User user) {
        String jwtToken = jwtService.generateToken(user);

        log.info("User in generateTokenAndAuthResponse method: {}", user.toString());
        log.info("JwtToken while building a response = {}", jwtToken);

        saveUserToken(user, jwtToken);

        String refreshJwtToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshJwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        log.info("This is authHeader {}", authHeader);

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            return;
        }

        refreshToken = authHeader.split(" ")[1].trim();

        log.info("This is jwtToken extracted from user claims = {} ", refreshToken);

        username = jwtService.extractUsername(refreshToken);

        log.info("username = {} ", username);

        if (username != null) {
//            User user = this.userService.getUser(username);
            User user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }

    }
}
