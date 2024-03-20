package com.sendi.v1.security.config;

import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.security.token.Token;
import com.sendi.v1.security.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private SecurityContextLogoutHandler securityContextHolder = new SecurityContextLogoutHandler();

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            return;
        }

        jwtToken = authHeader.split(" ")[1].trim();

        Token storedToken = tokenRepository.findByToken(jwtToken)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }

//        User user = (User) authentication.getPrincipal();
//        this.securityContextHolder.logout(request, response, authentication);
    }
}
