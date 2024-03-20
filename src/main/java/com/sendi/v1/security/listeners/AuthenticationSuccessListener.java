package com.sendi.v1.security.listeners;

import com.sendi.v1.security.domain.LoginSuccess;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent event) {
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            LoginSuccess.LoginSuccessBuilder loginSuccessBuilder = LoginSuccess.builder();

            if (token.getPrincipal() instanceof User) {
                User user = (User) token.getPrincipal();


                loginSuccessBuilder.user(user);
                log.info("User name logged in: {}", user.getUsername());
            }

            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();

            if (details instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails webAuthDetails = (WebAuthenticationDetails) details;

                loginSuccessBuilder.sourceIP(webAuthDetails.getRemoteAddress());
                log.info("Details IP source: {} ", webAuthDetails.getRemoteAddress());
            }

            loginSuccessRepository.save(loginSuccessBuilder.build());
        }
    }
}
